package com.karunadavanya.model

data class WildlifeItem(
    val id: String,
    val emoji: String,
    val name: String,
    val scientificName: String,
    val category: String,         // Animal, Bird, Plant
    val status: String,           // Endangered, Vulnerable, etc.
    val habitat: String,
    val diet: String,
    val weight: String,
    val description: String,
    val ecologicalRole: String
)

object WildlifeData {
    val items = listOf(
        WildlifeItem(
            id = "panther",
            emoji = "🐆",
            name = "Black Panther",
            scientificName = "Panthera pardus fusca",
            category = "Animal",
            status = "Vulnerable",
            habitat = "Kabini, Nagarhole forests",
            diet = "Deer, Wild Boar",
            weight = "60–90 kg",
            description = "The Black Panther is Karnataka's most iconic resident — a melanistic leopard whose dark coat is caused by a recessive gene. The Kabini reservoir area has one of the highest concentrations of black leopards in the world. They are solitary and elusive, hunting at dawn and dusk. Their presence indicates a healthy forest ecosystem.",
            ecologicalRole = "Apex predator that controls deer and boar populations, maintaining forest balance."
        ),
        WildlifeItem(
            id = "elephant",
            emoji = "🐘",
            name = "Asian Elephant",
            scientificName = "Elephas maximus",
            category = "Animal",
            status = "Endangered",
            habitat = "Nagarhole, Bandipur",
            diet = "Grass, Fruits, Bark",
            weight = "4,000–5,000 kg",
            description = "Asian Elephants are the largest land animals in Asia and are deeply revered in Karnataka culture. They are intelligent, emotional animals that live in matriarchal herds. Karnataka hosts the largest wild elephant population in India. They are crucial ecosystem engineers — their paths become forest roads used by hundreds of other species.",
            ecologicalRole = "Seed dispersers and habitat creators — they literally shape the landscape."
        ),
        WildlifeItem(
            id = "hornbill",
            emoji = "🦜",
            name = "Malabar Pied Hornbill",
            scientificName = "Anthracoceros coronatus",
            category = "Bird",
            status = "Near Threatened",
            habitat = "Western Ghats forests",
            diet = "Figs, Insects, Lizards",
            weight = "Wingspan: 90 cm",
            description = "The Hornbill is nature's farmer. It swallows fruits whole and deposits seeds far from the parent tree, regenerating forests. The female seals herself inside a tree hollow during nesting — the male feeds her through a small slit for months. Their loud calls echo across the canopy.",
            ecologicalRole = "Critical seed disperser — responsible for regenerating large-seeded trees."
        ),
        WildlifeItem(
            id = "sandalwood",
            emoji = "🌳",
            name = "Indian Sandalwood",
            scientificName = "Santalum album",
            category = "Plant",
            status = "Vulnerable",
            habitat = "Dry deciduous forests",
            diet = "Hemiparasitic on roots",
            weight = "Height: 8–12 metres",
            description = "The Sandalwood tree is Karnataka's State tree and one of the most valuable woods in the world. It is a hemiparasite — it draws nutrients from the roots of nearby plants. The aromatic heartwood takes 15–20 years to develop. Due to over-harvesting and poaching, wild populations have drastically declined.",
            ecologicalRole = "Supports biodiversity as host to dozens of bird and insect species."
        ),
        WildlifeItem(
            id = "tiger",
            emoji = "🐯",
            name = "Bengal Tiger",
            scientificName = "Panthera tigris tigris",
            category = "Animal",
            status = "Endangered",
            habitat = "Bandipur, Nagarhole, BRT",
            diet = "Sambar, Chital, Gaur",
            weight = "180–260 kg",
            description = "Karnataka is one of India's most important tiger states, home to reserves like Bandipur and Nagarhole. Tigers are solitary ambush hunters and require vast territories. India's Project Tiger has helped recover populations from under 1,800 in the 1970s to over 3,000 today.",
            ecologicalRole = "Top predator that regulates prey populations, keeping herbivore numbers balanced."
        )
    )
}
