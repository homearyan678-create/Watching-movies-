package com.example.data

import com.example.model.Episode
import com.example.model.MediaItem
import com.example.model.MediaType
import com.example.model.Season

object MovieCatalog {

    val worldwideCatalog: List<MediaItem> = listOf(
        // 1. Hollywood Sci-Fi Epic
        MediaItem(
            id = "dune-prophecy",
            title = "Dune: Prophecy",
            originalTitle = "Dune: Prophecy Worldwide",
            type = MediaType.SERIES,
            overview = "Set 10,000 years before the ascension of Paul Atreides, two Harkonnen sisters combat forces that threaten the future of humankind and establish the fabled sect known as the Bene Gesserit.",
            releaseYear = 2026,
            releaseDateString = "November 2026",
            rating = 9.1,
            contentRating = "TV-MA",
            is18Plus = false,
            genres = listOf("Sci-Fi", "Adventure", "Drama"),
            country = "Hollywood (USA)",
            language = "English / Dual Audio (Hindi)",
            director = "Alison Schapker",
            cast = listOf("Emily Watson", "Olivia Williams", "Travis Fimmel", "Jodhi May"),
            isTrending = true,
            isNewRelease = true,
            seasonCount = 1,
            backdropColorStart = 0xFF8C531B,
            backdropColorEnd = 0xFF2B1607,
            seasons = listOf(
                Season(
                    seasonNumber = 1,
                    title = "Season 1: Sisterhood Born",
                    episodes = listOf(
                        Episode("dp-s1-e1", 1, "The Hidden Hand", 62, "Valya Harkonnen establishes control over the emerging sisterhood as galactic tensions rise.", 540),
                        Episode("dp-s1-e2", 2, "Shadows of Arrakis", 58, "A mysterious reckoning on the sands uncovers ancestral secrets.", 510),
                        Episode("dp-s1-e3", 3, "Bloodlines Divided", 64, "Imperial political intrigue threatens to destroy the fragile alliance.", 570),
                        Episode("dp-s1-e4", 4, "The Prophecy Awakens", 60, "A vision shakes the Sisterhood's core dogma.", 530)
                    )
                )
            )
        ),

        // 2. Hollywood Blockbuster Movie
        MediaItem(
            id = "oppenheimer-imax",
            title = "Oppenheimer: 4K Director's Cut",
            type = MediaType.MOVIE,
            overview = "The story of American scientist J. Robert Oppenheimer and his role in the development of the atomic bomb, presented in pristine IMAX enhanced presentation.",
            releaseYear = 2024,
            releaseDateString = "Summer 2024",
            durationMinutes = 180,
            rating = 8.9,
            contentRating = "R",
            is18Plus = false,
            genres = listOf("Biography", "Drama", "History"),
            country = "Hollywood (USA)",
            language = "English / Dual Audio (Hindi)",
            director = "Christopher Nolan",
            cast = listOf("Cillian Murphy", "Emily Blunt", "Matt Damon", "Robert Downey Jr."),
            isTrending = true,
            isNewRelease = false,
            backdropColorStart = 0xFFB33912,
            backdropColorEnd = 0xFF1C0A04
        ),

        // 3. Bollywood High-Octane Action Thriller
        MediaItem(
            id = "jawan-extended",
            title = "Jawan: Uncut Extended Cut",
            type = MediaType.MOVIE,
            overview = "A high-octane action thriller highlighting the emotional journey of a prison warden driven by a personal vendetta to rectify society's corrupt wrongs.",
            releaseYear = 2024,
            releaseDateString = "Fall 2024",
            durationMinutes = 171,
            rating = 8.5,
            contentRating = "UA 16+",
            is18Plus = false,
            genres = listOf("Action", "Thriller", "Masala"),
            country = "Bollywood (India)",
            language = "Hindi / Tamil / Telugu / English",
            director = "Atlee",
            cast = listOf("Shah Rukh Khan", "Nayanthara", "Vijay Sethupathi", "Deepika Padukone"),
            isTrending = true,
            isNewRelease = false,
            backdropColorStart = 0xFF9E1212,
            backdropColorEnd = 0xFF1F0303
        ),

        // 4. Bollywood Crime Drama Web Series (Cult Classic)
        MediaItem(
            id = "mirzapur-s3",
            title = "Mirzapur: King of Purvanchal",
            type = MediaType.SERIES,
            overview = "The throne of Mirzapur remains bathed in blood and betrayal as Guddu Pandit and Golu clash with the fiercest crime lords in Northern India.",
            releaseYear = 2025,
            releaseDateString = "2025",
            rating = 8.8,
            contentRating = "18+ Adult",
            is18Plus = true,
            genres = listOf("Crime", "Action", "Drama", "Thriller"),
            country = "Bollywood (India)",
            language = "Hindi / Urdu",
            director = "Gurmmeet Singh",
            cast = listOf("Pankaj Tripathi", "Ali Fazal", "Shweta Tripathi", "Rasika Dugal"),
            isTrending = true,
            isNewRelease = false,
            seasonCount = 3,
            backdropColorStart = 0xFF691010,
            backdropColorEnd = 0xFF140202,
            seasons = listOf(
                Season(
                    seasonNumber = 3,
                    title = "Season 3: The Blood Dynasty",
                    episodes = listOf(
                        Episode("mzp-s3-e1", 1, "Tetua", 55, "Guddu asserts dominance while rivals circle the throne.", 490),
                        Episode("mzp-s3-e2", 2, "Gaddi Ka Khel", 52, "Sharad Shukla orchestrates an alliance across the state border.", 460),
                        Episode("mzp-s3-e3", 3, "Badla Aur Barood", 59, "Old scores turn deadly in a nighttime ambush.", 520)
                    )
                )
            )
        ),

        // 5. Korean Mega Hit Series
        MediaItem(
            id = "squid-game-s2",
            title = "Squid Game 2: The Final Bet",
            type = MediaType.SERIES,
            overview = "Three years after winning Player 456 returns with a resolute mission to uncover who is behind the deadly games and put an end to the horrific tournament.",
            releaseYear = 2026,
            releaseDateString = "October 2026",
            rating = 9.2,
            contentRating = "TV-MA",
            is18Plus = true,
            genres = listOf("Thriller", "Suspense", "Mystery", "Drama"),
            country = "South Korea",
            language = "Korean / Dual Audio (English, Hindi)",
            director = "Hwang Dong-hyuk",
            cast = listOf("Lee Jung-jae", "Lee Byung-hun", "Wi Ha-joon", "Yim Si-wan"),
            isTrending = true,
            isNewRelease = true,
            seasonCount = 2,
            backdropColorStart = 0xFFB51052,
            backdropColorEnd = 0xFF240212,
            seasons = listOf(
                Season(
                    seasonNumber = 2,
                    title = "Season 2: Return to the Arena",
                    episodes = listOf(
                        Episode("sg-s2-e1", 1, "Bread and Lottery", 58, "Gi-hun tracks down the recruiter and infiltrates the underground system.", 510),
                        Episode("sg-s2-e2", 2, "The Red Line", 61, "A newly modified childhood game tests the morality of a desperate squad.", 530),
                        Episode("sg-s2-e3", 3, "Behind The Iron Mask", 65, "The Front Man confronts an insurrection inside the security quarters.", 580)
                    )
                )
            )
        ),

        // 6. Japanese Anime Masterpiece Series
        MediaItem(
            id = "attack-on-titan-final",
            title = "Attack on Titan: The Rumbling",
            type = MediaType.SERIES,
            overview = "Eren Yeager unleashes the Colossal Titans to flatten the earth. His former comrades and sworn enemies must unite in an impossible last stand for humanity.",
            releaseYear = 2024,
            releaseDateString = "2024",
            rating = 9.4,
            contentRating = "TV-MA",
            is18Plus = false,
            genres = listOf("Anime", "Dark Fantasy", "Action", "Sci-Fi"),
            country = "Japan",
            language = "Japanese / English Dubbed / Hindi Dubbed",
            director = "Yuichiro Hayashi",
            cast = listOf("Yuki Kaji", "Yui Ishikawa", "Marina Inoue", "Hiroshi Kamiya"),
            isTrending = true,
            isNewRelease = false,
            seasonCount = 4,
            backdropColorStart = 0xFF1C2C40,
            backdropColorEnd = 0xFF080D14,
            seasons = listOf(
                Season(
                    seasonNumber = 4,
                    title = "Final Season Part 3 & 4",
                    episodes = listOf(
                        Episode("aot-e1", 1, "The Rumbling: First Wave", 65, "The colossal advance reaches the mainland coast.", 580),
                        Episode("aot-e2", 2, "Battle of Heaven and Earth", 72, "The alliance leaps from the airship onto the Founding Titan.", 640),
                        Episode("aot-e3", 3, "Toward the Tree on That Hill", 85, "The heart-wrenching final duel determines the destiny of Eldia.", 750)
                    )
                )
            )
        ),

        // 7. 18+ Mature Action / Crime (Explicit user request for 18+ content support)
        MediaItem(
            id = "tokyo-underworld-18",
            title = "Tokyo Underworld: Sin City (18+)",
            type = MediaType.SERIES,
            overview = "A ruthless neo-noir saga exploring the illicit shadow economy of Shinjuku. An undercover operative descends into the blood-soaked power struggle between rival syndicates. Contains intense violence and mature themes.",
            releaseYear = 2026,
            releaseDateString = "November 2026",
            rating = 8.7,
            contentRating = "18+ Adult",
            is18Plus = true,
            genres = listOf("Crime", "Action", "Neo-Noir", "Mature Thriller"),
            country = "Japan / International",
            language = "Japanese / English / Dual Audio",
            director = "Kenji Sato",
            cast = listOf("Tadanobu Asano", "Hiroyuki Sanada", "Rinko Kikuchi"),
            isTrending = true,
            isNewRelease = true,
            seasonCount = 1,
            backdropColorStart = 0xFF850D27,
            backdropColorEnd = 0xFF1C0207,
            seasons = listOf(
                Season(
                    seasonNumber = 1,
                    title = "Season 1: Neon Blood",
                    episodes = listOf(
                        Episode("tu-s1-e1", 1, "Kabukicho Midnight (18+)", 54, "A drug deal gone wrong leaves the syndicate clan in flames.", 480),
                        Episode("tu-s1-e2", 2, "Yakuza Protocol (18+)", 51, "Torture, loyalty, and betrayal collide inside the VIP club basement.", 450),
                        Episode("tu-s1-e3", 3, "Red Lantern Execution (18+)", 57, "Retaliation strikes during a rain-drenched rooftop shootout.", 510)
                    )
                )
            )
        ),

        // 8. 18+ Psychological Dark Thriller (Explicit 18+ content)
        MediaItem(
            id = "dark-asylum-18",
            title = "The Dark Asylum: Uncut (18+)",
            type = MediaType.MOVIE,
            overview = "A forensic psychiatrist investigates a locked high-security ward on an isolated island where patients possess hypnotic psychological control over the staff. Disturbing imagery and uncensored psychological terror.",
            releaseYear = 2025,
            releaseDateString = "2025",
            durationMinutes = 118,
            rating = 8.4,
            contentRating = "18+ Adult",
            is18Plus = true,
            genres = listOf("Psychological Horror", "Mystery", "18+ Thriller"),
            country = "United Kingdom",
            language = "English",
            director = "Arthur Vance",
            cast = listOf("David Tennant", "Rebecca Hall", "Paul Bettany"),
            isTrending = false,
            isNewRelease = false,
            backdropColorStart = 0xFF2F0F3D,
            backdropColorEnd = 0xFF0B030F
        ),

        // 9. European Heist Series
        MediaItem(
            id = "berlin-money-heist",
            title = "Berlin: The Paris Heist",
            type = MediaType.SERIES,
            overview = "During his golden age, Berlin gathers a masterful gang in Paris for one of his most ambitious robberies ever: disappearing 44 million euros in jewels.",
            releaseYear = 2024,
            releaseDateString = "2024",
            rating = 8.1,
            contentRating = "TV-MA",
            is18Plus = false,
            genres = listOf("Action", "Heist", "Crime", "Mystery"),
            country = "Spain (Europe)",
            language = "Spanish / Dual Audio (English, Hindi)",
            director = "Álex Pina",
            cast = listOf("Pedro Alonso", "Michelle Jenner", "Tristán Ulloa", "Begoña Vargas"),
            isTrending = true,
            isNewRelease = false,
            seasonCount = 1,
            backdropColorStart = 0xFF144D59,
            backdropColorEnd = 0xFF05171C,
            seasons = listOf(
                Season(
                    seasonNumber = 1,
                    title = "Season 1",
                    episodes = listOf(
                        Episode("ber-e1", 1, "The Energy of Love", 53, "Berlin assembles his crew near the catacombs of Paris.", 470),
                        Episode("ber-e2", 2, "Anchor and Lobo", 50, "The underground excavation nears the vault.", 440),
                        Episode("ber-e3", 3, "Full House Poker", 56, "A close call with the Parisian gendarmerie.", 500)
                    )
                )
            )
        ),

        // 10. Bollywood Crime Mafia
        MediaItem(
            id = "sacred-games-dark",
            title = "Sacred Games: Dark Chronicles (18+)",
            type = MediaType.SERIES,
            overview = "Sartaj Singh uncovers a cataclysmic conspiracy tied to the enigmatic gangster Ganesh Gaitonde. Raw language, gritty underworld conflict, and political conspiracies.",
            releaseYear = 2024,
            releaseDateString = "2024",
            rating = 8.6,
            contentRating = "18+ Adult",
            is18Plus = true,
            genres = listOf("Crime", "Drama", "Mystery", "18+ Thriller"),
            country = "Bollywood (India)",
            language = "Hindi / Urdu",
            director = "Anurag Kashyap & Vikramaditya Motwane",
            cast = listOf("Saif Ali Khan", "Nawazuddin Siddiqui", "Radhika Apte"),
            isTrending = false,
            isNewRelease = false,
            seasonCount = 2,
            backdropColorStart = 0xFF54340A,
            backdropColorEnd = 0xFF120A01
        ),

        // 11. Hollywood Action 4K
        MediaItem(
            id = "deadpool-wolverine-4k",
            title = "Deadpool & Wolverine 4K Extended",
            type = MediaType.MOVIE,
            overview = "Wolverine is recovering from his injuries when he crosses paths with the loudmouth Deadpool. They team up to defeat a common enemy in a wild multiverse brawl.",
            releaseYear = 2024,
            releaseDateString = "Fall 2024",
            durationMinutes = 128,
            rating = 8.2,
            contentRating = "R",
            is18Plus = false,
            genres = listOf("Action", "Comedy", "Sci-Fi"),
            country = "Hollywood (USA)",
            language = "English / Dual Audio (Hindi)",
            director = "Shawn Levy",
            cast = listOf("Ryan Reynolds", "Hugh Jackman", "Emma Corrin", "Morena Baccarin"),
            isTrending = true,
            isNewRelease = false,
            backdropColorStart = 0xFF9E1B1B,
            backdropColorEnd = 0xFF1C0A1E
        ),

        // 12. K-Drama Romance & Revenge
        MediaItem(
            id = "the-glory-uncut",
            title = "The Glory: Uncut Revenge (18+)",
            type = MediaType.SERIES,
            overview = "Years after surviving horrific school violence, a woman puts an elaborate revenge scheme into motion to make the perpetrators pay for their crimes.",
            releaseYear = 2024,
            releaseDateString = "2024",
            rating = 8.9,
            contentRating = "18+ Adult",
            is18Plus = true,
            genres = listOf("Revenge", "Drama", "Psychological", "18+"),
            country = "South Korea",
            language = "Korean / Dual Audio (English, Hindi)",
            director = "Ahn Gil-ho",
            cast = listOf("Song Hye-kyo", "Lee Do-hyun", "Lim Ji-yeon"),
            isTrending = true,
            isNewRelease = false,
            seasonCount = 1,
            backdropColorStart = 0xFF212529,
            backdropColorEnd = 0xFF0D0E10
        )
    )

    // Dynamic new releases that can be unlocked or simulated via push notification updates!
    val upcomingDrops: List<MediaItem> = listOf(
        MediaItem(
            id = "interstellar-beyond",
            title = "Interstellar: Beyond Gargantua (4K Remaster)",
            type = MediaType.MOVIE,
            overview = "A newly restored, unseen cut featuring extended space exploration sequences and uncompressed Dolby Atmos master tracks.",
            releaseYear = 2026,
            releaseDateString = "Just Released Today",
            durationMinutes = 175,
            rating = 9.3,
            contentRating = "PG-13",
            is18Plus = false,
            genres = listOf("Sci-Fi", "Adventure", "Drama"),
            country = "Hollywood (USA)",
            language = "English / Hindi",
            director = "Christopher Nolan",
            cast = listOf("Matthew McConaughey", "Anne Hathaway", "Jessica Chastain"),
            isTrending = true,
            isNewRelease = true,
            backdropColorStart = 0xFF0B2447,
            backdropColorEnd = 0xFF030A14
        ),
        MediaItem(
            id = "narcos-cartel-wars-18",
            title = "Narcos: Cartel Wars (Uncut 18+)",
            type = MediaType.SERIES,
            overview = "A gripping inside look at the international syndicate wars across Mexico and South America with uncensored, gritty realism.",
            releaseYear = 2026,
            releaseDateString = "Just Dropped",
            rating = 8.9,
            contentRating = "18+ Adult",
            is18Plus = true,
            genres = listOf("Crime", "Action", "Biography", "18+ Thriller"),
            country = "Latin America / Hollywood",
            language = "Spanish / English / Dual Audio",
            director = "Andres Baiz",
            cast = listOf("Pedro Pascal", "Wagner Moura", "Diego Luna"),
            isTrending = true,
            isNewRelease = true,
            seasonCount = 1,
            backdropColorStart = 0xFF7A1507,
            backdropColorEnd = 0xFF1F0401
        ),
        MediaItem(
            id = "demon-slayer-infinity",
            title = "Demon Slayer: Infinity Castle Arc",
            type = MediaType.MOVIE,
            overview = "The Demon Slayer Corps plunge into the Infinite Castle to wage the final catastrophic battle against Muzan Kibutsuji and the Upper Ranks.",
            releaseYear = 2026,
            releaseDateString = "World Premiere Today",
            durationMinutes = 135,
            rating = 9.5,
            contentRating = "R",
            is18Plus = false,
            genres = listOf("Anime", "Action", "Supernatural"),
            country = "Japan",
            language = "Japanese / English / Hindi",
            director = "Haruo Sotozaki",
            cast = listOf("Natsuki Hanae", "Akari Kito", "Hiro Shimono"),
            isTrending = true,
            isNewRelease = true,
            backdropColorStart = 0xFF571059,
            backdropColorEnd = 0xFF140217
        )
    )
}
