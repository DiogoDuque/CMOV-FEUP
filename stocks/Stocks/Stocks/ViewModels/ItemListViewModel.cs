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
                Nick = "APPL",
                CurrentQuote = 1,
                ImageUrl = "apple.png"
            },
            new Company
            {
                Name = "IBM",
                Id = 2,
                Nick = "IBM",
                CurrentQuote = 1,
                ImageUrl = "ibm.png"
            },
            new Company
            {
                Name = "Hewlett Packard",
                Id = 3,
                Nick = "HPE",
                CurrentQuote = 1,
                ImageUrl = "hp.png"
            },
            new Company
            {
                Name = "Microsoft",
                Id = 4,
                Nick = "MSFT",
                CurrentQuote = 1,
                ImageUrl = "microsoft.png"
            },
            new Company
            {
                Name = "Oracle",
                Id = 5,
                Nick = "ORCL",
                CurrentQuote = 1,
                ImageUrl = "oracle.png"
            },
            new Company
            {
                Name = "Google",
                Id = 6,
                Nick = "GOOGL",
                CurrentQuote = 1,
                ImageUrl = "google.png"
            },
            new Company
            {
                Name = "Facebook",
                Id = 7,
                Nick = "FB",
                CurrentQuote = 1,
                ImageUrl = "facebook.png"
            },
            new Company
            {
                Name = "Twitter",
                Id = 8,
                Nick = "TWTR",
                CurrentQuote = 1,
                ImageUrl = "twitter.png"
            },
            new Company
            {
                Name = "Intel",
                Id = 9,
                Nick = "INTC",
                CurrentQuote = 1,
                ImageUrl = "intel.png"
            },
            new Company
            {
                Name = "AMD",
                Id = 10,
                Nick = "AMD",
                CurrentQuote = 1,
                ImageUrl = "amd.png"
            }
        };
    }

    public void SetValue(){
        for (int i = 0; i < Companies.Count; i++)
            Companies[i].CurrentQuote = 0;
    }
}