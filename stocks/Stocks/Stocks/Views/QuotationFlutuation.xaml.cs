using System;
using System.Collections.Generic;
using Stocks.Models;
using Xamarin.Forms;

namespace Stocks.Views
{
    public partial class QuotationFlutuation : ContentPage
    {
        private List<Company> companies;
        public QuotationFlutuation(List<Company> companies)
        {
            this.companies = companies;
            InitializeComponent();
        }
    }
}
