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
        SKCanvasView view;
        public QuotationFlutuation(List<Company> companies)
        {
            this.companies = companies;
            title = new Label()
            {
                HorizontalOptions = LayoutOptions.Center,
                VerticalOptions = LayoutOptions.Center,
                TextColor = Color.Black,
                FontSize = 20,
                FontAttributes = FontAttributes.Bold,
                Margin = new Thickness(0, 20, 0, 30),
                Text = (companies.Count == 1 ? "Quotation flutuation of " + companies[0].Name : "Quotation flutuation of " + companies[0].Name + " and " + companies[1].Name)
            };

            view = new SKCanvasView() {
            };

            view.PaintSurface += OnPainting;


            Content = new StackLayout()
            {
                Children = { title, view }
            };

            BackgroundColor = Color.White;
        }

        private void OnPainting(object sender, SKPaintSurfaceEventArgs e)
        {
            // we get the current surface from the event args
            var surface = e.Surface;
            // then we get the canvas that we can draw on
            var canvas = surface.Canvas;
            // clear the canvas / view
            canvas.Clear(SKColors.White);
        }
    }
}
