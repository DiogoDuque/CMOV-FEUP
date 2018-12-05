using System.Collections.ObjectModel;
using Stocks.Models;

public class ItemListViewModel
{
    public ObservableCollection<Company> Companies { get; set; }

    public ItemListViewModel()
    {
        this.Companies = new ObservableCollection<Company>
        {
            new Company
            {
                Name = "Apple",
                Id = 1,
                Nick = "AAPL",
                CurrentQuote = 1,
                ImageUrl = "apple.png",
                Type = "Red"
            },
            new Company
            {
                Name = "IBM",
                Id = 2,
                Nick = "IBM",
                CurrentQuote = 1,
                ImageUrl = "ibm.png",
                Type = "Blue"
            },
            new Company
            {
                Name = "Hewlett Packard",
                Id = 3,
                Nick = "HPE",
                CurrentQuote = 1,
                ImageUrl = "hp.png",
                Type = "Green"
            },
            new Company
            {
                Name = "Microsoft",
                Id = 4,
                Nick = "MSFT",
                CurrentQuote = 1,
                ImageUrl = "microsoft.png",
                Type = "Green"
            },
            new Company
            {
                Name = "Oracle",
                Id = 5,
                Nick = "ORCL",
                CurrentQuote = 1,
                ImageUrl = "oracle.png",
                Type = "Green"
            },
            new Company
            {
                Name = "Google",
                Id = 6,
                Nick = "GOOG",
                CurrentQuote = 1,
                ImageUrl = "google.jpg",
                Type = "Green"
            },
            new Company
            {
                Name = "Facebook",
                Id = 7,
                Nick = "FB",
                CurrentQuote = 1,
                ImageUrl = "facebook.png",
                Type = "Green"
            },
            new Company
            {
                Name = "Twitter",
                Id = 8,
                Nick = "TWTR",
                CurrentQuote = 1,
                ImageUrl = "twitter.png",
                Type = "Red"
            },
            new Company
            {
                Name = "Intel",
                Id = 9,
                Nick = "INTC",
                CurrentQuote = 1,
                ImageUrl = "intel.png",
                Type = "Blue"
            },
            new Company
            {
                Name = "AMD",
                Id = 10,
                Nick = "AMD",
                CurrentQuote = 1,
                ImageUrl = "amd.png",
                Type = "Red"
            }
        };
    }

    public void SetValue(){
        for (int i = 0; i < Companies.Count; i++)
        {
            Companies[i].CurrentQuote = 0;
            Companies[i].Type = "Blue";
            //se mantem quote fica azul, se desce fica a vermelho e se sobe fica a verde
        }
    }
}