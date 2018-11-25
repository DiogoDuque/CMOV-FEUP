using System.Collections.ObjectModel;
using Stocks.Models;
using Stocks.ViewModels;

public class ItemListViewModel : BaseViewModel
{
    public ObservableCollection<Company> Companies { get; set; }

    public ItemListViewModel()
    {
        this.Companies = new ObservableCollection<Company>();
        //Just for tesing
        this.Companies.Add(new Company
        {
            Name = "Apple",
            Id = 1,
            Nick = "APPL"
        });
    }
}