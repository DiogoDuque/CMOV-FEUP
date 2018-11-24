using System;

using Xamarin.Forms;

namespace Stocks
{
    public class ListCompanies : ContentPage
    {
        public ListCompanies()
        {
            Content = new StackLayout
            {
                Children = {
                    new Label { Text = "Hello ContentPage" }
                }
            };
        }
    }
}

