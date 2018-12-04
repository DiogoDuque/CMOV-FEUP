using System;
using System.Collections.Generic;

using Xamarin.Forms;

namespace Stocks.Views
{
    public partial class ChangeIP : ContentPage
    {
        public ChangeIP()
        {
            InitializeComponent();

            saveButton.Clicked += SaveButton_Clicked;
        }

        void SaveButton_Clicked(object sender, EventArgs e)
        {
            String ip = IP.Text;

            if (ip.Length != 0)
            {
                Navigation.PushModalAsync(new ItemListPage());
            }
            else
                DisplayAlert("Alert", "You have to write your server's IP address", "OK");
        }

    }
}
