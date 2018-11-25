using System;
using System.Collections.Generic;
using Stocks.Models;
using Xamarin.Forms;

namespace Stocks.Views
{
    public partial class ItemListPage : ContentPage
    {
        ItemListViewModel itemListViewModel;
        List<Company> companies = new List<Company>();

        public ItemListPage()
        {
            InitializeComponent();
            itemListViewModel = new ItemListViewModel();
            BindingContext = itemListViewModel;

           // listView.ItemSelected += ListView_ItemSelected;
           // nextButton.Clicked += NextButton_Clicked;
        }

        void NextButton_Clicked(object sender, EventArgs e)
        {
            if(companies.Count != 0)
                Application.Current.MainPage = new QuotationFlutuation(companies);
            else
                DisplayAlert("Alert", "You have to select 1 or 2 companies", "OK");
        }


        void ListView_ItemSelected(object sender, SelectedItemChangedEventArgs e)
        {
           // String selectedFromList = listView.SelectedItem.ToString();

            foreach(var company in itemListViewModel.Companies){
                /*if (company.Name == selectedFromList){
                    if (companies.Count < 2)
                        companies.Add(company);
                    else
                        DisplayAlert("Alert", "You have to select only 1 or 2 companies", "OK");
                }*/
            }
        }

    }
}
