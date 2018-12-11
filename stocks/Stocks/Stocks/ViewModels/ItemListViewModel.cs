using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Net.Http;
using Newtonsoft.Json;
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
                symbol = "AAPL",
                netChange = 1,
                ImageUrl = "apple.png",
                Type = "Red"
            },
            new Company
            {
                Name = "IBM",
                Id = 2,
                symbol = "IBM",
                netChange = 1,
                ImageUrl = "ibm.png",
                Type = "Blue"
            },
            new Company
            {
                Name = "Hewlett Packard",
                Id = 3,
                symbol = "HPE",
                netChange = 1,
                ImageUrl = "hp.png",
                Type = "Green"
            },
            new Company
            {
                Name = "Microsoft",
                Id = 4,
                symbol = "MSFT",
                netChange = 1,
                ImageUrl = "microsoft.png",
                Type = "Green"
            },
            new Company
            {
                Name = "Oracle",
                Id = 5,
                symbol = "ORCL",
                netChange = 1,
                ImageUrl = "oracle.png",
                Type = "Green"
            },
            new Company
            {
                Name = "Google",
                Id = 6,
                symbol = "GOOG",
                netChange = 1,
                ImageUrl = "google.jpg",
                Type = "Green"
            },
            new Company
            {
                Name = "Facebook",
                Id = 7,
                symbol = "FB",
                netChange = 1,
                ImageUrl = "facebook.png",
                Type = "Green"
            },
            new Company
            {
                Name = "Twitter",
                Id = 8,
                symbol = "TWTR",
                netChange = 1,
                ImageUrl = "twitter.png",
                Type = "Red"
            },
            new Company
            {
                Name = "Intel",
                Id = 9,
                symbol = "INTC",
                netChange = 1,
                ImageUrl = "intel.png",
                Type = "Blue"
            },
            new Company
            {
                Name = "AMD",
                Id = 10,
                symbol = "AMD",
                netChange = 1,
                ImageUrl = "amd.png",
                Type = "Red"
            }
        };
    }

    public async void SetValue()
    {
        string basePath = Network.GetQuote();
        var uri = new Uri(string.Format(basePath, string.Empty));
        var client = new HttpClient
        {
            MaxResponseContentBufferSize = 256000
        };
        var response = await client.GetAsync(uri);
        if (response.IsSuccessStatusCode)
        {
            var content = await response.Content.ReadAsStringAsync();
            List<Company> companiesVals = JsonConvert.DeserializeObject<List<Company>>(content);
            foreach(Company comp in Companies)
            {
                foreach(Company compVal in companiesVals)
                {
                    if(comp.symbol.Equals(compVal.symbol))
                    {
                        comp.netChange = compVal.netChange;
                        comp.percentChange = compVal.percentChange;

                        if (comp.netChange < 0)
                            comp.Type = "Red";
                        else if(comp.netChange > 0)
                            comp.Type = "Green";
                        else
                            comp.Type = "Blue";
                         
                        companiesVals.Remove(compVal);
                        break;
                    }
                }
            }
        }
    }

    /*public void SetValue(){
        for (int i = 0; i < Companies.Count; i++)
        {
            Companies[i].CurrentQuote = 0;
            Companies[i].Type = "Blue";
            //se mantem quote fica azul, se desce fica a vermelho e se sobe fica a verde
        }
    }*/
}