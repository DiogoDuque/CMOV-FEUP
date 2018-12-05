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
        List<CompanyHistory> companyHistories;

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

            GetHistory();
        }

        private async void GetHistory()
        {
            var uri = new Uri(string.Format("http://192.168.1.247:8080/history?company="+this.companies[0].Nick+"&period=7", string.Empty));
            var client = new HttpClient();
            client.MaxResponseContentBufferSize = 256000;
            var response = await client.GetAsync(uri);
            if (response.IsSuccessStatusCode)
            {
                var content = await response.Content.ReadAsStringAsync();
                companyHistories = JsonConvert.DeserializeObject<List<CompanyHistory>>(content);
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
                isSelected = false;
                view.InvalidateSurface();
            }
            else
            {
                typeActivate.Text = "7 days analysis activated.\nTouch graph to change view.";
                isSelected = true;
                view.InvalidateSurface();
            }
        }

        private void OnPainting(object sender, SKPaintSurfaceEventArgs e)
        {
            SKImageInfo info = e.Info;
            SKSurface surface = e.Surface;
            SKCanvas canvas = surface.Canvas;
            int maxWidth = (int)Math.Round(info.Width * 0.95);
            int maxHeight = (int)Math.Round(info.Height * 0.95);
            int minWidth = (int)Math.Round(info.Width * 0.05);
            int minHeight = (int)Math.Round(info.Height * 0.05);

            // clear canvas
            canvas.Clear(SKColors.LightGray);

            if (companyHistories == null)
                return;

            //set paint
            SKPaint paint = new SKPaint()
            {
                IsAntialias = true,
                //IsStroke = true,
                Color = new SKColor(0x2c, 0x3e, 0x50),
                StrokeCap = SKStrokeCap.Round,
                StrokeWidth = 5,
            };

            List<SKPoint> points = new List<SKPoint>();
            double closeMax = Double.MinValue;
            double closeMin = Double.MaxValue;
            List<double> closes = new List<double>();
            foreach(var item in companyHistories)
            {
                //var tradingDay = item.tradingDay;
                var close = item.close;
                if (close > closeMax)
                    closeMax = close;
                if (close < closeMin)
                    closeMin = close;
                closes.Add(close);
            }

            double xStep = ((double)maxWidth - minWidth)/closes.Count;
            double yFactor = (maxHeight-minHeight)/(closeMax-closeMin);
            for(int i=0; i<closes.Count; i++)
            {
                if (i == 0)
                    continue;
                double prevClose = closes[i - 1];
                double currentClose = closes[i];

                int prevY = (int) Math.Round((prevClose - closeMin) * yFactor + minHeight);
                int currentY = (int)Math.Round((currentClose - closeMin) * yFactor + minHeight);

                int prevX = (int) Math.Round(minWidth + (i-1)*xStep);
                int currentX = (int)Math.Round(minWidth + i * xStep);

                //DisplayAlert("Debug", "prev=("+prevX+ ","+prevY+"); current=(" + currentX + "," + currentY + ")", "OK");

                canvas.DrawLine(new SKPoint(prevX, prevY), new SKPoint(currentX, currentY), paint);

            }
            
            //TODO draw different according to isSelected
        }
    }
}
