using System;
using System.Collections.Generic;
using Stocks.Models;
using Xamarin.Forms;
using SkiaSharp;
using SkiaSharp.Views.Forms;

namespace Stocks.Views
{
    public partial class QuotationFlutuation : ContentPage
    {
        private List<Company> companies;
        Label title;
        Label typeActivate;
        Button back;
        SKCanvasView view;
        Boolean isSelected;
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
                Margin = new Thickness(0, 10, 0, 0),

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
            var surface = e.Surface;
            var canvas = surface.Canvas;

            canvas.Clear(SKColors.LightGray);

            SKPaint paint = new SKPaint();
            paint.IsAntialias = true;
            paint.Color = new SKColor(0x2c, 0x3e, 0x50);
            paint.StrokeCap = SKStrokeCap.Round;

            canvas.DrawCircle(info.Width / 2, info.Height / 2, 100, paint);
            canvas.DrawCircle(info.Width/3, info.Height/3, 100, paint);
            //TODO draw different according to isSelected
        }
    }
}
