using System;
using Stocks.ViewModels;

namespace Stocks.Models
{
    public class Company : BaseViewModel
    {
        public string Name { get; set; }
        public int Id { get; set; }
        public string symbol { get; set; }
        private double _CurrentQuote;
        public double netChange { get => _CurrentQuote; set => SetProperty(ref _CurrentQuote, value); }
        public double percentChange { get; set; }
        public String ImageUrl { get; set; }
        private String _Type;
        public String Type { get => _Type; set => SetProperty(ref _Type, value); }
    }
}

