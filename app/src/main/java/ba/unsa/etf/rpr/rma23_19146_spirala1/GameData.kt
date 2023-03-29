package ba.unsa.etf.rpr.rma23_19146_spirala1

class GameData {
    companion object Data {
        fun getAll() : List<Game>{
            return arrayListOf<Game>(Game("Elden Ring","PC","25.02.2022",9.0,"eldenring","M",
                "FromSoftware Inc.","Bandai Namco","Soulsborne",
                "Elden Ring[a] is a 2022 action role-playing game developed by FromSoftware and " +
                        "published by Bandai Namco Entertainment. Directed by Hidetaka Miyazaki with worldbuilding " +
                        "provided by fantasy writer George R. R. Martin, it was released for PlayStation 4, PlayStation 5," +
                        " Windows, Xbox One, and Xbox Series X/S on February 25. In the game, players control a customizable" +
                        " player character on a journey to repair the titular Elden Ring and become the new Elden Lord.",
                arrayListOf<UserImpression>(UserReview("Exoticer",123,"Difficult, but once you learn the mechanics, you can't stop playing"),
                UserRating("ebabic1",321,8.5),UserReview("Kiwi Clicker",111,"⠀⠀⠀⠀⠀⠀⠀⠀⠀⡄⠀⡼⠃⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\n" +
                            "⠀⠀⠀⠀⠀⠀⠀⠀⣸⣿⣿⣶⡾⠟⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\n" +
                            "⠀⠀⠀⠀⠀⠀⠀⣰⣿⣿⣿⣅⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\n" +
                            "⠀⠀⠀⠀⣠⣴⣿⡿⠋⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\n" +
                            "⠀⣀⣴⣾⣿⡿⠋⠀⣠⣶⣶⠿⠿⠿⠿⠷⢶⣶⣤⣄⠀⠀⠀⠀⠀⠀⠀⠀\n" +
                            "⢰⣿⣿⣿⣟⠀⠀⢸⣿⣿⣥⣤⣤⣄⣀⣀⣠⣬⣭⣿⠁⠀⠀⠀⠀⠀⠀⠀\n" +
                            "⠀⠉⠻⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣷⣤⣤⣄⠀⠀⠀⠀\n" +
                            "⠀⠀⠀⠀⢿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣦⣄⠀\n" +
                            "⠀⠀⠀⠀⠀⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⡏⠈⠙⠿⣿⣿\n" +
                            "⠀⠀⠀⠀⠀⢿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⠃⠀⠀⠀⣀⣹\n" +
                            "⠀⠀⠀⠀⠀⠘⢿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⠏⣀⣤⣶⡿⠿⠟\n" +
                            "⠀⠀⠀⠀⠀⠀⠈⠻⠿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⠿⠋⠰⣿⠇⠀⠀⠀⠀\n" +
                            "⠀⠀⠀⠀⠀⠀⠀⣀⣤⣤⡍⠉⠉⠙⠛⠛⠋⣩⣥⣤⣀⠀⠀⠀⠀⠀⠀⠀\n" +
                            "⠀⠀⠀⠀⠀⠀⠻⢿⡋⠉⠀⠀⠀⠀⠀⠀⠀⠀⠉⣻⡿⠿⠂"), UserReview("Morally Grey",1,"Very good game. First playthrough can be a pain, especially with lvling and finding the lvl appropriate areas but at least you get to break the game if you know where to go. Lore is good." +
                            " Story is hard to understand at first. Gameplay is really good. Visuals are fantastic." +
                            " Not much more to say, just buy and play."), UserReview("Grist",2,"I am not a \"Dark Souls\" player. I have tried them and bounced off hard because I am just not that coordinated nor patient. That said, this game rapidly became one of the 5 best video games I have ever played. And I've been playing them since the 80's.\n" +
                            "\n" +
                            "If you like exploration and adventure, this is the game for you. And as a byproduct I've actually gotten better at combat. Like actual skill.\n" +
                            "\n" +
                            "I'm now over 100 hours in, and I've only defeated the 1st main boss because I'm having so much fun with exploring. I haven't had a moment of boredom or feeling I've played this out. I'm hoping it is another 100 hours because I'd rather it didn't end. I'm hoping to stretch it out until the DLC.")
                )
            ),Game("Counter-Strike: Global Offensive","PC","21.08.2012",9.0,"csgo","M","Valve","Valve",
                "FPS","Counter-Strike: Global Offensive (CS: GO) expands upon the team-based action gameplay that it pioneered when it was launched " +
                        "19 years ago. CS: GO features new maps, characters, weapons, and game modes, and delivers updated versions of the classic CS content (de_dust2, etc.).",
                arrayListOf<UserImpression>(UserReview("Critiquing Doge",1,"Many guns. Such competitive. Very intense. 9/10 paws."),UserReview("Orginal Curators Group",2,"Probably the best FPS that's currently available on Steam, everyone should at least try this game 10/10."),
                    UserRating("ebabic1",3,9.1),UserReview("What Game Engine",4,"Source Engine, C++; Some other programs they use kHED, 3DsMax, Blender, Cinema 4D, fragMOTION, gMax, Maya, MilkShape 3D, LightWave 3D, Autodesk Softimage, SoftImage Mod Tool, Wings 3D, and Clara.io.")
                    ,UserReview("Laccsed Curating Group",5,"Fun team game, you usually play against players of the same skill level. Strategy is not that important in the lower levels but becomes difficult later on. Aim comes with time so does game sense.")))
                ,Game("Borderlands 3","PC","13.09.2019",9.0,"borderlands3","M","Gearbox Software","2K Games",
            "first-person shooter","Borderlands 3 is a 2019 action role-playing first-person shooter video game developed by Gearbox Software and published" +
                        " by 2K. It is a sequel to 2012's Borderlands 2, and the fourth entry in the main Borderlands series." +
                        " Borderlands 3 was released on 13 September 2019 for PlayStation 4, Windows, and Xbox One," +
                        " and released for macOS on 30 October 2019. A Stadia port was released on 17 December 2019." +
                        " Versions for the Xbox Series X and Series S and PlayStation 5 including free upgrades for users on the prior console versions were released on 10 " +
                        "and 12 November 2020, respectively.",arrayListOf<UserImpression>()),
            Game("Don't Starve Together","PC","21.04.2016",10.0,"don_tstarvetogether","T",
            "Klei Entertainment","Klei Entertainment","Open World Survival Craft","Fight, Farm, Build and Explore Together in the standalone multiplayer " +
                        "expansion to the uncompromising wilderness survival game, Don't Starve.",arrayListOf<UserImpression>())
            ,Game("Terraria","PC","16.05.2011",10.0,"terraria","T","Re-Logic","Re-Logic",
                    "Sandbox","Dig, fight, explore, build! Nothing is impossible in this action-packed adventure game.",arrayListOf<UserImpression>()),
            Game("Left 4 Dead 2","PC","17.11.2009",10.0,"l4d2","M","Valve","Valve","Zombies",
            "Set in the zombie apocalypse, Left 4 Dead 2 (L4D2) is the highly anticipated sequel to the award-winning Left 4 Dead, the #1 co-op game of 2008" +
                    ". This co-operative action horror FPS takes you and your friends through the cities, swamps and cemeteries of the Deep South, from Savannah to " +
                    "New Orleans",arrayListOf<UserImpression>()),
                Game("Apex Legends","PC","04.02.2019",9.0,"apexlegends","T",
                    "Respawn Entertainment","Electronic Arts","Battle royale",
                    "Apex Legends is a free-to-play battle royale-hero shooter game developed " +
                            "by Respawn Entertainment and published by Electronic Arts. It was released " +
                            "for PlayStation 4, Windows, and Xbox One in February 2019, for Nintendo Switch in March 2021," +
                            " and for PlayStation 5 and Xbox Series X/S in March 2022. A mobile version of the game designed for " +
                            "touchscreens titled Apex Legends Mobile was released in May 2022 on Android and iOS. The game supports" +
                            " cross-platform play, excluding the aforementioned mobile platforms.",
                    arrayListOf<UserImpression>()),
                    Game("League of Legends","PC","27.10.2009",7.6,"leagueoflegends",
                        "E","Riot Games","Riot Game","MOBA","League of Legends (LoL), commonly referred to as League, is a 2009 multiplayer " +
                                "online battle arena video game developed and published by Riot Games. Inspired by Defense of the Ancients, a custom map for Warcraft III, Riot's founders " +
                                "sought to develop a stand-alone game in the same genre. Since its release in October 2009, League has been free-to-play and is monetized through purchasable" +
                                " character customization. The game is available for Microsoft Windows and macOS.",
                        arrayListOf<UserImpression>()),
                    Game("VALORANT","PC","02.06.2020",9.0,"valorant","T","Riot Games","Riot Games",
                        "FPS","Valorant is a free-to-play first-person tactical hero shooter developed and published by Riot Games, for Windows.[2] Teased under the codename " +
                                "Project A in October 2019, the game began a closed beta period with limited access on April 7, 2020, followed by a release on June 2, 2020. " +
                                "The development of the game started in 2014. Valorant takes inspiration from the Counter-Strike series of tactical shooters, borrowing several mechanics such" +
                                " as the buy menu, spray patterns, and inaccuracy while moving.",arrayListOf<UserImpression>()),
                    Game("Team Fortress 2","PC","10.10.2007",9.0,"teamfortress2","M","Valve","Valve","Hero Shooter","Nine distinct" +
                            " classes provide a broad range of tactical abilities and personalities. Constantly updated with new game modes, maps, equipment and, most importantly, hats!",arrayListOf<UserImpression>())
            )


        }
        fun getDetails(title: String): Game {
            val game = getAll().find{ it.title == title}
            return game?: Game(" "," "," ", 0.0," "," "," "," "," "," ", arrayListOf());
        }
    }
}