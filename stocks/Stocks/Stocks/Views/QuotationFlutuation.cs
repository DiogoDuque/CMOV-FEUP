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
        Button button;
        Button back;
        SKCanvasView view;
        Boolean isSelected;
        public QuotationFlutuation(List<Company> companies)
        {
            this.companies = companies;
            title = new Label()
            {
                HorizontalOptions = LayoutOptions.Center,
                VerticalOptions = LayoutOptions.Center,
                HorizontalTextAlignment = TextAlignment.Center,
                TextColor = Color.Black,
                FontSize = 18,
                FontAttributes = FontAttributes.Bold,
                Margin = new Thickness(0, 40, 0, 30),
                Text = (companies.Count == 1 ? "Quotation flutuation of " + companies[0].Name : "Quotation flutuation of " + companies[0].Name + " and " + companies[1].Name)
            };

            typeActivate = new Label()
            {
                HorizontalOptions = LayoutOptions.Center,
                VerticalOptions = LayoutOptions.Center,
                HorizontalTextAlignment = TextAlignment.Center,
                TextColor = Color.FromHex("#019fc6"),
                FontSize = 17,
                FontAttributes = FontAttributes.Bold,
                Margin = new Thickness(0, 0, 0, 15),
                Text= "7 days analysis activated"
            };

            button = new Button() {
                Text = "30 days",
                HorizontalOptions = LayoutOptions.Center,
                VerticalOptions = LayoutOptions.Center,
                WidthRequest = 100,
                BackgroundColor = Color.FromHex("#019fc6"),
                TextColor = Color.White,
                CornerRadius = 20,
                FontAttributes = FontAttributes.Bold,
                Margin = new Thickness(0,0,0,10)
            };

            back = new Button()
            {
                Text = "Back",
                HorizontalOptions = LayoutOptions.Center,
                VerticalOptions = LayoutOptions.Center,
                WidthRequest = 100,
                BackgroundColor = Color.FromHex("#019fc6"),
                TextColor = Color.White,
                CornerRadius = 20,
                FontAttributes = FontAttributes.Bold,
                Margin = new Thickness(0, 20, 0, 20)
            };

            view = new SKCanvasView() {
            };

            view.PaintSurface += OnPainting;


            Content = new StackLayout()
            {
                VerticalOptions = LayoutOptions.CenterAndExpand,
                HorizontalOptions = LayoutOptions.CenterAndExpand,
                Children = { title, typeActivate, button, view, back }
            };

            BackgroundColor = Color.White;

            isSelected = true;

            button.Clicked += Button_Clicked;
            back.Clicked += Back_Clicked;
        }

        void Back_Clicked(object sender, EventArgs e)
        {
            Navigation.PushModalAsync(new ItemListPage());
        }


        void Button_Clicked(object sender, EventArgs e)
        {
            if (isSelected)
            {
                typeActivate.Text = "30 days analysis activated";
                button.Text = "7 days";
                isSelected = false;
            }
            else
            {
                typeActivate.Text = "7 days analysis activated";
                button.Text = "30 days";
                isSelected = true;
            }

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
