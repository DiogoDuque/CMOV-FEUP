using System;

using Xamarin.Forms;

namespace Stocks.Models
{
    public class Company
    {
        public string Name { get; set; }
        public int Id { get; set; }
        public string Nick { get; set; }
        public double CurrentQuote { get; set; }
        public String ImageUrl { get; set; }
    }
}

