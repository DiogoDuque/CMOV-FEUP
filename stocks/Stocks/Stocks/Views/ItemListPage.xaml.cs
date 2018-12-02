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

            listView.ItemSelected += ListView_ItemSelected;
            nextButton.Clicked += NextButton_Clicked;
        }

        void NextButton_Clicked(object sender, EventArgs e)
        {
            if(companies.Count != 0){
                Navigation.PushModalAsync(new QuotationFlutuation(companies));
            }
            else
                DisplayAlert("Alert", "You have to select 1 or 2 companies", "OK");
        }


        void ListView_ItemSelected(object sender, SelectedItemChangedEventArgs e)
        {
            var selectedFromList = (Company)e.SelectedItem;
            string name = selectedFromList.Name;

            foreach(Company company in itemListViewModel.Companies){
                if (company.Name == name){
                    if (companies.Count < 2)
                        companies.Add(company);
                    else
                        DisplayAlert("Alert", "You have to select only 1 or 2 companies", "OK");
                }
            }
        }

        protected void ListItems_Refreshing(object sender, EventArgs e)
        {
            listView.BeginRefresh();
            DoRefresh();       
            listView.EndRefresh();  
        }

        void DoRefresh(){
            itemListViewModel.SetValue();
            BindingContext = itemListViewModel;
        }
    }
}
