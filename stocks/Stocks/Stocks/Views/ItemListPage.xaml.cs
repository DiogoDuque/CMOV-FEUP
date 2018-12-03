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

            listView.ItemTapped += ListView_ItemSelected;
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


        void ListView_ItemSelected(object sender, ItemTappedEventArgs e)
        {
            Company selectedFromList = (Company) e.Item;
            string name = selectedFromList.Name;

            foreach(Company company in itemListViewModel.Companies){
                if (company.Name == name){
                    if (!companies.Contains(company) && companies.Count < 2)
                    {
                        selectLabel.IsVisible = true;
                        selectedCompanies.IsVisible = true;

                        companies.Add(company);

                        if (companies.Count == 1)
                            selectedCompanies.Text += company.Name;
                        else
                            selectedCompanies.Text += " and " + company.Name;
                    }
                    else if (companies.Contains(company))
                    {
                        companies.Remove(company);

                        if (companies.Count == 0)
                        {
                            selectedCompanies.Text = "";
                            selectedCompanies.IsVisible = false;
                            selectLabel.IsVisible = false;
                        }
                        else
                            selectedCompanies.Text = companies[0].Name;
                    }
                    else if(companies.Count >= 2)
                        DisplayAlert("Alert", "You have to select only 1 or 2 companies", "OK");
                }
            }
        }

        void ListItems_Refreshing(object sender, EventArgs e)
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
