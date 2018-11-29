using System.Collections.ObjectModel;
using Stocks.Models;
using Stocks.ViewModels;

public class ItemListViewModel : BaseViewModel
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
                CurrentQuote = 1
            },
            new Company
            {
                Name = "IBM",
                Id = 2,
                Nick = "IBM",
                CurrentQuote = 1
            },
            new Company
            {
                Name = "Hewlett Packard",
                Id = 3,
                Nick = "Hewlett Packard",
                CurrentQuote = 1
            },
            new Company
            {
                Name = "Microsoft",
                Id = 4,
                Nick = "Microsoft",
                CurrentQuote = 1
            },
            new Company
            {
                Name = "Oracle",
                Id = 5,
                Nick = "Oracle",
                CurrentQuote = 1
            },
            new Company
            {
                Name = "Google",
                Id = 6,
                Nick = "Google",
                CurrentQuote = 1
            },
            new Company
            {
                Name = "Facebook",
                Id = 7,
                Nick = "Facebook",
                CurrentQuote = 1
            },
            new Company
            {
                Name = "Twitter",
                Id = 8,
                Nick = "Twitter",
                CurrentQuote = 1
            },
            new Company
            {
                Name = "Intel",
                Id = 9,
                Nick = "Intel",
                CurrentQuote = 1
            },
            new Company
            {
                Name = "AMD",
                Id = 10,
                Nick = "AMD",
                CurrentQuote = 1
            }
        };
    }
}