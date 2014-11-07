package com.marshalchen.common.demoofui.recyclerviewstickyheaders.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by aurel on 11/10/14.
 */
public class PersonDataProvider {

    private LinkedHashMap<String, Boolean> items;
    private List<String> addedItems;

    public PersonDataProvider() {
        this.items = new LinkedHashMap<String, Boolean>();
        this.addedItems = new ArrayList<String>();

        for (int i = 1; i < persons.length; i += 2 ) {
            items.put(persons[i - 1], true);
            items.put(persons[i], false);
        }

        buildAddedItems();
    }


    public List<String> getItems() {
        return addedItems;
    }

    public void remove(int position) {
        items.put(addedItems.get(position), false);
        buildAddedItems();
    }

    public int insertAfter(int position) {
        String addAfter = addedItems.get(position);
        Iterator<String> iterator = items.keySet().iterator();
        String next = iterator.next();

        while (iterator.hasNext() && !next.equals(addAfter)) {
            next = iterator.next();
        }

        do {
            next = iterator.next();
        }
        while (iterator.hasNext() && items.get(next));

        items.put(next, true);
        buildAddedItems();

        return addedItems.lastIndexOf(next);
    }

    private void buildAddedItems() {
        addedItems.clear();
        for (Map.Entry<String, Boolean> entry : items.entrySet()) {
            if (entry.getValue()) {
                addedItems.add(entry.getKey());
            }
        }
        //Collections.sort(addedItems);
    }

    private static String[] persons = {
            "Abram Tavernia",
            "Alexa Oquin",
            "Alvin Lainez",
            "Alyce Rakestraw",
            "Angel Scruggs",
            "Annabel Wardle",
            "Ardella Hollinger",
            "Arlean Drewes",
            "Armida Carasco",
            "Asa Modeste",
            "Ashlea Aguillard",
            "Aurore Maris",
            "Bao Godbold",
            "Bettye Wenger",
            "Bill Thatch",
            "Brad Amis",
            "Bridget Goulette",
            "Bryan Rarick",
            "Camie Malcolm",
            "Caridad Nesbitt",
            "Carleen Maul",
            "Carmelo Ehrmann",
            "Caroll Ruben",
            "Cherryl Suter",
            "Christeen Bonner",
            "Christene Thrailkill",
            "Cindie Luong",
            "Claudio Llanos",
            "Cleveland Selvage",
            "Clint Cullen",
            "Clora Graybeal",
            "Cristin Culton",
            "Crysta Bolt",
            "Cuc Hetzel",
            "Daine Cumbie",
            "Danuta Villalta",
            "Darci Quick",
            "Darius Hermes",
            "Delaine Evins",
            "Delpha Godin",
            "Dexter Bencomo",
            "Dione Rhines",
            "Donella Blumstein",
            "Dorene Kisling",
            "Dudley Benavides",
            "Dulce Demille",
            "Ebonie Wallis",
            "Effie Wiley",
            "Elayne Munro",
            "Elisha Funches",
            "Elna Padua",
            "Emmy Denk",
            "Farrah Delosantos",
            "Frieda Buesing",
            "Gilda Tse",
            "Gina Dufault",
            "Giovanna Schepis",
            "Glayds Mcguigan",
            "Glinda Dunagan",
            "Gwenda Fraser",
            "Hai Oday",
            "Halley Holscher",
            "Hellen Baillie",
            "Herbert Renninger",
            "Hobert Yopp",
            "Hollis Haubert",
            "Hui Lupien",
            "Ileen Mccasland",
            "Imelda Moser",
            "Ione Littlewood",
            "Jacalyn Gressett",
            "Jacquelyn Butter",
            "Jade Churchwell",
            "Jami Selph",
            "Janeth Ringwood",
            "Jeffry Carcamo",
            "Jerlene Zellers",
            "Jerome Tomko",
            "Jerrod Rother",
            "Jettie Conner",
            "Joaquin Keplinger",
            "Joette Healey",
            "Jorge Molina",
            "Juana Olds",
            "Jules Friley",
            "Julio Krier",
            "Kareen Bergey",
            "Katharyn Doten",
            "Katherine Ragsdale",
            "Kathryn Edgington",
            "Katia Hock",
            "Keeley Pass",
            "Kendrick Moncada",
            "Kenyetta Wick",
            "Kimber Boulware",
            "Kitty Manthe",
            "Kristan Blake",
            "Lakeisha Medlin",
            "Lakesha Voth",
            "Lanora Pair",
            "Lashon Abramson",
            "Laurie Campa",
            "Laurinda Barcus",
            "Lavern Puig",
            "Lera Mckibben",
            "Long Show",
            "Louanne Garling",
            "Louella Petillo",
            "Lucinda Sockwell",
            "Lyla Fitzsimons",
            "Mackenzie Ooten",
            "Malia Claiborne",
            "Manie Yarberry",
            "Marchelle Halcomb",
            "Marcie Augusta",
            "Marguerita Tenaglia",
            "Mari Sheperd",
            "Mariela Ruggieri",
            "Marielle Connolly",
            "Marilyn Franck",
            "Marisol Marmolejo",
            "Marth Pitchford",
            "Marty Cobey",
            "Maximo Thornburg",
            "Meggan Plumadore",
            "Mellissa Schnitzer",
            "Melodie Kitch",
            "Mimi File",
            "Mina Nolte",
            "Mira Archuleta",
            "Modesto Higgenbotham",
            "Mohammed Orr",
            "Morgan Maddy",
            "Morgan Mensch",
            "Moriah Grubb",
            "Nedra Dyson",
            "Norene Nelms",
            "Odis Mill",
            "Ok Hutter",
            "Olin Bolander",
            "Otilia Dejulio",
            "Otis Shore",
            "Patrina Crystal",
            "Philip Wengert",
            "Porter Ketner",
            "Rhiannon Lavoie",
            "Richard Domingues",
            "Rochell Molock",
            "Rosalva Gutman",
            "Rosalyn Pesce",
            "Rosaria Rosengarten",
            "Roxane Clayborn",
            "Rozanne Mahaney",
            "Sal Wilkinson",
            "Saundra Lundahl",
            "Scotty Ralph",
            "Seema Boots",
            "Selena Eisenhower",
            "Shae Hellard",
            "Shae Latz",
            "Shantay Wilcox",
            "Shawnda Kees",
            "Shayne Cutler",
            "Shenita Cassette",
            "Sherie Culp",
            "Sherrie Poole",
            "Shirley Cliett",
            "Shizue Alcaraz",
            "Sid Streets",
            "Stacia Twellman",
            "Stasia Slay",
            "Stephen Eagles",
            "Svetlana Hallam",
            "Tambra Buchner",
            "Tamie Branham",
            "Tammera Hutt",
            "Tawanna Rameriz",
            "Tawna Seim",
            "Terisa Whitbeck",
            "Terresa Brantley",
            "Terri Barnaby",
            "Tinisha Gammill",
            "Todd Netter",
            "Toshiko Skowron",
            "Traci Schurr",
            "Trish Perino",
            "Tyesha Bruemmer",
            "Valda Skyles",
            "Vella Montilla",
            "Venita Richarson",
            "Vera Noffsinger",
            "Vinnie Gobeil",
            "Waltraud Nelsen",
            "Wendy Zachery",
            "Willard Qualls",
            "Willetta Zucker",
            "Yen Staton",
            "Yolonda Hadnott",
            "Yoshie Califano",
            "Yu Schilke"
    };

}
