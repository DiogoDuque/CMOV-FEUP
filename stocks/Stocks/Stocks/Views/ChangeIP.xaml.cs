using System;
using System.Text.RegularExpressions;
using Plugin.Connectivity;
using Stocks.Models;
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
                if (CrossConnectivity.Current.IsConnected)
                {
                    string pattern = @"^http://\d{1,3}\.\d{1,3}\.\d{1,3}\.\d{1,3}:\d{1,5}$";

                    Regex regex = new Regex(pattern, RegexOptions.Compiled | RegexOptions.IgnoreCase);

                    string url = ip.Trim();

                    if (regex.IsMatch(url))
                    {
                        Network.IP = ip;
                        Navigation.PushModalAsync(new ItemListPage());
                    }
                    else
                        DisplayAlert("Alert", "The IP address isn't a valid URL", "OK");
                }
                else
                    DisplayAlert("Alert", "You aren't connected to Internet", "OK");
            }
            else
                DisplayAlert("Alert", "You have to write your server's IP address", "OK");
        }

    }
}
