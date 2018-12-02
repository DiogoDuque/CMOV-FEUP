using System;
using System.Collections.Generic;

using Xamarin.Forms;

namespace Stocks.Views
{
    public partial class MainPage : ContentPage
    {
        public MainPage()
        {
            InitializeComponent();
        }

        public void OnButtonTapped(object sender, EventArgs e)
        {
            Navigation.PushModalAsync(new ItemListPage());
        }
    }
}
