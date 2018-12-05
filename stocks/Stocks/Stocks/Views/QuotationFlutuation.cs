using System;
using System.Collections.Generic;
using Stocks.Models;
using Xamarin.Forms;
using SkiaSharp;
using SkiaSharp.Views.Forms;
using Newtonsoft.Json;
using System.Net.Http;

namespace Stocks.Views
{
    public partial class QuotationFlutuation : ContentPage
    {
        class CompanyHistory
        {
            public string tradingDay;
            public double close;
        }

        private List<Company> companies;
        Label title;
        Label typeActivate;
        Button back;
        SKCanvasView view;
        Boolean isSelected;
        List<List<CompanyHistory>> companiesHistory;

        public QuotationFlutuation(List<Company> companies)
        {
            this.companies = companies;

            back = new Button()
            {
                Text = "Back",
                HorizontalOptions = LayoutOptions.Start,
                WidthRequest = 100,
                BackgroundColor = Color.FromHex("#019fc6"),
                TextColor = Color.White,
                CornerRadius = 20,
                FontAttributes = FontAttributes.Bold,
                Margin = new Thickness(0, 50, 0, 0),

            };

            title = new Label()
            {
                HorizontalOptions = LayoutOptions.Center,
                HorizontalTextAlignment = TextAlignment.Center,
                TextColor = Color.Black,
                FontSize = 18,
                FontAttributes = FontAttributes.Bold,
                Margin = new Thickness(0, 20, 0, 0),
                Text = (companies.Count == 1 ? "Quotation flutuation of " + companies[0].Name : "Quotation flutuation of " + companies[0].Name + " and " + companies[1].Name)
            };

            typeActivate = new Label()
            {
                HorizontalOptions = LayoutOptions.Center,
                HorizontalTextAlignment = TextAlignment.Center,
                TextColor = Color.FromHex("#019fc6"),
                FontSize = 17,
                FontAttributes = FontAttributes.Bold,
                Margin = new Thickness(0, 25, 0, 0),
                Text= "7 days analysis activated.\nTouch graph to change view."
            };
            
            view = new SKCanvasView() {
                HeightRequest = 250,
                Margin = new Thickness(0, 15, 0, 0)
            };

            view.PaintSurface += OnPainting;
            TapGestureRecognizer tap = new TapGestureRecognizer();
            tap.Tapped += OnCanvasTap;
            view.GestureRecognizers.Add(tap);

            Content = new StackLayout()
            {
                VerticalOptions = LayoutOptions.Start,
                HorizontalOptions = LayoutOptions.FillAndExpand,
                Margin = 10,
                Children = { back, title, typeActivate, view }
            };

            BackgroundColor = Color.White;
            isSelected = true;
            back.Clicked += Back_Clicked;

            this.GetHistory();
        }

        private async void GetHistory()
        {
            companiesHistory = null;
            view.InvalidateSurface();

            const string basePath = "http://192.168.1.247:8080/history?";
            string companyStr = "company=" + companies[0].Nick + (companies.Count>1 ? "&company="+companies[1].Nick : "");
            string periodStr = "&period=" + (isSelected ? 7 : 30);
            var uri = new Uri(string.Format(basePath+companyStr+periodStr, string.Empty));
            var client = new HttpClient();
            client.MaxResponseContentBufferSize = 256000;
            var response = await client.GetAsync(uri);
            if (response.IsSuccessStatusCode)
            {
                var content = await response.Content.ReadAsStringAsync();
                companiesHistory = JsonConvert.DeserializeObject<List<List<CompanyHistory>>>(content);
                view.InvalidateSurface();
            }
        }

        void Back_Clicked(object sender, EventArgs e)
        {
            Navigation.PushModalAsync(new ItemListPage());
        }

        private void OnCanvasTap(object sender, EventArgs args)
        {
            if (isSelected)
            {
                typeActivate.Text = "30 days analysis activated.\nTouch graph to change view.";
            }
            else
            {
                typeActivate.Text = "7 days analysis activated.\nTouch graph to change view.";
            }
            isSelected = !isSelected;
            GetHistory();
        }

        private void OnPainting(object sender, SKPaintSurfaceEventArgs e)
        {
            // set needed vars
            SKImageInfo info = e.Info;
            SKSurface surface = e.Surface;
            SKCanvas canvas = surface.Canvas;
            int maxWidth = (int)Math.Round(info.Width * 0.95);
            int maxHeight = (int)Math.Round(info.Height * 0.95);
            int minWidth = (int)Math.Round(info.Width * 0.1);
            int minHeight = (int)Math.Round(info.Height * 0.08);
            int circleRadius = (int)Math.Round((double)info.Width / 125);

            // clear canvas
            canvas.Clear(SKColors.LightGray);

            //set paints
            SKPaint[] graphLinePaint = new SKPaint[]
            {
                new SKPaint()
                {
                    IsAntialias = true,
                    Color = new SKColor(0x00, 0x00, 0xcc),
                    StrokeCap = SKStrokeCap.Round,
                    StrokeWidth = 6,
                    TextSize = 30,
                },
                new SKPaint()
                {
                    IsAntialias = true,
                    Color = new SKColor(0x00, 0xa0, 0x00),
                    StrokeCap = SKStrokeCap.Round,
                    StrokeWidth = 6,
                    TextSize = 30,
                },
            };
            SKPaint circlePaint = new SKPaint()
            {
                IsAntialias = true,
                Color = new SKColor(0xcc, 0x64, 0x64),
                StrokeCap = SKStrokeCap.Round,
            };

            for(int i=0; i<companies.Count; i++)
            {
                float width = info.Width * (0.20f + i * 0.40f);
                float height = maxHeight - (maxHeight - minHeight) * 1.03f;
                canvas.DrawText(companies[i].Name, new SKPoint(width, height), graphLinePaint[i]);
            }

            if (companiesHistory == null)
                return;

            // calculate boundaries and rescale values
            double closeMax = Double.MinValue;
            double closeMin = Double.MaxValue;
            List<List<double>> closes = new List<List<double>>();
            List<string> days = new List<string>();
            for(int i=0; i<companiesHistory.Count; i++)
            {
                var companyHistory = companiesHistory[i];
                List<double> tmpCloses = new List<double>();
                foreach(var item in companyHistory)
                {
                    if(i==0)
                    {
                        days.Add(item.tradingDay);
                    }

                    var close = item.close;
                    tmpCloses.Add(close);
                    if (close > closeMax)
                        closeMax = close;
                    if (close < closeMin)
                        closeMin = close;
                }
                closes.Add(tmpCloses);
            }
            double xStep = ((double)maxWidth - minWidth)/(days.Count-1);
            closeMin = companies.Count > 1 ? closeMax - 1.1 * (closeMax - closeMin) : closeMin;
            double yFactor = (maxHeight-minHeight)/ (closeMax - closeMin);

            // draw graph axis and aux lines
            DrawGraphAxis(canvas, days, new SKPoint(minWidth, minHeight), new SKPoint(maxWidth, maxHeight), xStep, closeMin, closeMax);

            // draw graph
            for(int c=0; c<companiesHistory.Count; c++)
            {
                for (int i = 0; i < days.Count; i++)
                {
                    double currentClose = closes[c][i];
                    int currentX = (int)Math.Round(minWidth + i * xStep);
                    int currentY = maxHeight - (int)Math.Round((currentClose - closeMin) * yFactor);
                    
                    canvas.DrawCircle(new SKPoint(currentX, currentY), circleRadius, circlePaint);

                    if (i == 0)
                        continue;

                    double prevClose = closes[c][i - 1];

                    int prevY = maxHeight - (int)Math.Round((prevClose - closeMin) * yFactor);
                    int prevX = (int)Math.Round(minWidth + (i-1) * xStep);

                    canvas.DrawLine(new SKPoint(prevX, prevY), new SKPoint(currentX, currentY), graphLinePaint[c]);
                }
            }
        }

        private void DrawGraphAxis(SKCanvas canvas, List<string> days, SKPoint min, SKPoint max, double xStep, double closeMin, double closeMax)
        {
            const int graphLines = 4;
            double heightDiff = max.Y - min.Y;
            double heightStep = heightDiff / graphLines;
            double closeStep = (closeMax - closeMin) / graphLines;

            SKPaint axisPaint = new SKPaint()
            {
                Color = new SKColor(0x00, 0x00, 0x00),
                StrokeCap = SKStrokeCap.Square,
                StrokeWidth = 10,
            };
            SKPaint auxLinePaint = new SKPaint()
            {
                Color = new SKColor(0xA0, 0xA0, 0xA0),
                StrokeCap = SKStrokeCap.Butt,
                StrokeWidth = 2,
            };
            SKPaint textPaint = new SKPaint()
            {
                Color = new SKColor(0x1a, 0x1a, 0x1a),
                TextSize = 22,
            };

            SKPoint origin = new SKPoint(min.X, max.Y);
            canvas.DrawLine(origin, new SKPoint(min.X, min.Y), axisPaint);
            canvas.DrawLine(origin, new SKPoint(max.X, max.Y), axisPaint);

            for(int i=1; i<=graphLines; i++)
            {
                int height = (int) Math.Round(max.Y - i * heightStep);
                double close = Math.Round(closeMax + i * closeStep, 2);
                canvas.DrawLine(new SKPoint(min.X, height), new SKPoint(max.X, height), auxLinePaint);
                canvas.DrawText(""+close, new SKPoint(0, height), textPaint);
            }

            int daysSkip = 5;
            int inc = isSelected ? 1 : daysSkip;
            double widthDiff = max.X - min.X;
            double widthStep = widthDiff / daysSkip;
            double widthOffset = widthDiff / 14;
            for(int i=0; i<days.Count; i+=inc)
            {
                int width = (int)Math.Round(min.X + i * xStep);
                if(i!=0)
                {
                    canvas.DrawLine(new SKPoint(width, min.Y), new SKPoint(width, max.Y), auxLinePaint);
                }
                canvas.DrawText(days[i], new SKPoint(width - (float)widthOffset, min.Y + (float)heightDiff*1.05f), textPaint);
            }
        }
    }
}
