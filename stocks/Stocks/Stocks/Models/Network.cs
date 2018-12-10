using System;
namespace Stocks.Models
{
    public static class Network
    {
        public static string IP { get; set; }
        public static string GetHistory() { return IP + "/history?"; }
        public static string GetQuote() { return IP + "/quote"; }
    }
}
