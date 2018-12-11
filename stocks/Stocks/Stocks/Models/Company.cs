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
        private double _CurrentPercent;
        public double netChange { get => _CurrentQuote; set => SetProperty(ref _CurrentQuote, value); }
        public double percentChange { get => _CurrentPercent; set => SetProperty(ref _CurrentPercent, value); }
        public String ImageUrl { get; set; }
        private String _Type;
        public String Type { get => _Type; set => SetProperty(ref _Type, value); }
    }
}

