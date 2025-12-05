<script lang="ts">
import {defineComponent} from 'vue'
import {IParty} from "@/interfaces/IParty";
import IconArrow from "@/components/icons/IconArrow.vue";

export default defineComponent({
  name: "PartyDetails",
  components: {IconArrow},
  setup() {
    const party: IParty = {
      "id": 60,
      "pid": 1,
      "name": "PVV (Partij voor de Vrijheid)",
      "seats": 0,
      "candidates": [
        {
          "id": 1,
          "initials": "G.",
          "firstName": "Geert",
          "lastName": "Wilders",
          "gender": "male",
          "locality": "'s-Gravenhage",
          "elected": true
        },
        {
          "id": 2,
          "initials": "T.S.M.",
          "firstName": "Sebastiaan",
          "lastName": "Stöteler",
          "gender": "male",
          "locality": "Almelo",
          "elected": true
        },
        {
          "id": 3,
          "initials": "J.M.M.",
          "firstName": "Shanna",
          "lastName": "Schilder",
          "gender": "female",
          "locality": "Volendam",
          "elected": true
        },
        {
          "id": 4,
          "initials": "A.N.",
          "firstName": "Annelotte",
          "lastName": "Lammers",
          "gender": "female",
          "locality": "Genemuiden",
          "elected": true
        },
        {
          "id": 5,
          "initials": "G.",
          "firstName": "Gidi",
          "lastName": "Markuszower",
          "gender": "male",
          "locality": "Amsterdam",
          "elected": true
        },
        {
          "id": 6,
          "initials": "M.",
          "firstName": "Martin",
          "lastName": "Bosma",
          "gender": "male",
          "locality": "Amsterdam",
          "elected": true
        },
        {
          "id": 7,
          "initials": "R.A.B.",
          "firstName": "René",
          "lastName": "Claassen",
          "gender": "male",
          "locality": "Landgraaf",
          "elected": true
        },
        {
          "id": 8,
          "initials": "A.P.C.",
          "firstName": "Tony",
          "lastName": "Dijck",
          "gender": "male",
          "locality": "Amsterdam",
          "elected": true
        },
        {
          "id": 9,
          "initials": "M.H.M.",
          "firstName": "Marjolein",
          "lastName": "Faber",
          "gender": "female",
          "locality": "Hoevelaken",
          "elected": true
        },
        {
          "id": 10,
          "initials": "V.",
          "firstName": "Vicky",
          "lastName": "Maeijer",
          "gender": "female",
          "locality": "Krimpen aan den IJssel",
          "elected": true
        },
        {
          "id": 11,
          "initials": "E.",
          "firstName": "Edgar",
          "lastName": "Mulder",
          "gender": "male",
          "locality": "Zwolle",
          "elected": true
        },
        {
          "id": 12,
          "initials": "A.",
          "firstName": "Alexander",
          "lastName": "Kops",
          "gender": "male",
          "locality": "Overasselt",
          "elected": true
        },
        {
          "id": 13,
          "initials": "E.",
          "firstName": "Elmar",
          "lastName": "Vlottes",
          "gender": "male",
          "locality": "Apeldoorn",
          "elected": true
        },
        {
          "id": 14,
          "initials": "M.",
          "firstName": "Marina",
          "lastName": "Vondeling",
          "gender": "female",
          "locality": "Rhoon",
          "elected": true
        },
        {
          "id": 15,
          "initials": "H.D.",
          "firstName": "Hidde",
          "lastName": "Heutink",
          "gender": "male",
          "locality": "Overdinkel",
          "elected": true
        },
        {
          "id": 16,
          "initials": "J.",
          "firstName": "Jeremy",
          "lastName": "Mooiman",
          "gender": "male",
          "locality": "Zoetermeer",
          "elected": true
        },
        {
          "id": 17,
          "initials": "N.",
          "firstName": "Nicole",
          "lastName": "Moinat",
          "gender": "female",
          "locality": "Purmerend",
          "elected": true
        },
        {
          "id": 18,
          "initials": "D.J.G.",
          "firstName": "Dion",
          "lastName": "Graus",
          "gender": "male",
          "locality": "Heerlen",
          "elected": true
        },
        {
          "id": 19,
          "initials": "E.",
          "firstName": "Emiel",
          "lastName": "Dijk",
          "gender": "male",
          "locality": "'s-Gravenhage",
          "elected": true
        },
        {
          "id": 20,
          "initials": "T.",
          "firstName": "Tamara",
          "lastName": "Hove",
          "gender": "female",
          "locality": "Emst",
          "elected": true
        },
        {
          "id": 21,
          "initials": "E.C.",
          "firstName": "Erwin",
          "lastName": "Prickaertz",
          "gender": "male",
          "locality": "Dordrecht",
          "elected": true
        },
        {
          "id": 22,
          "initials": "R.",
          "firstName": "Raymond",
          "lastName": "Roon",
          "gender": "male",
          "locality": "Aardenburg",
          "elected": true
        },
        {
          "id": 23,
          "initials": "M.C.H.",
          "firstName": "Maikel",
          "lastName": "Boon",
          "gender": "male",
          "locality": "Bergen op Zoom",
          "elected": true
        },
        {
          "id": 24,
          "initials": "A.J.",
          "firstName": "Annette",
          "lastName": "Raijer",
          "gender": "female",
          "locality": "Almere",
          "elected": true
        },
        {
          "id": 25,
          "initials": "R.F.",
          "firstName": "Rachel",
          "lastName": "Meetelen",
          "gender": "female",
          "locality": "Bergen op Zoom",
          "elected": true
        },
        {
          "id": 26,
          "initials": "C.A.",
          "firstName": "Chris",
          "lastName": "Jansen",
          "gender": "male",
          "locality": "Almere",
          "elected": true
        },
        {
          "id": 27,
          "initials": "M.",
          "firstName": "Max",
          "lastName": "Aardema",
          "gender": "male",
          "locality": "Drachten",
          "elected": true
        },
        {
          "id": 28,
          "initials": "M.",
          "firstName": "Marco",
          "lastName": "Deen",
          "gender": "male",
          "locality": "Zandvoort",
          "elected": true
        },
        {
          "id": 29,
          "initials": "J.M.",
          "firstName": "Jeanet",
          "lastName": "Nijhof-Leeuw",
          "gender": "female",
          "locality": "Hengelo (O)",
          "elected": true
        },
        {
          "id": 30,
          "initials": "J.H.",
          "firstName": "Henk",
          "lastName": "Vree",
          "gender": "male",
          "locality": "Hardinxveld-Giessendam",
          "elected": true
        },
        {
          "id": 31,
          "initials": "J.",
          "firstName": "Joeri",
          "lastName": "Pool",
          "gender": "male",
          "locality": "Kampen",
          "elected": true
        },
        {
          "id": 32,
          "initials": "P.H.",
          "firstName": "Patrick",
          "lastName": "Hoeff",
          "gender": "male",
          "locality": "Philippine",
          "elected": true
        },
        {
          "id": 33,
          "initials": "D.",
          "firstName": "Dennis",
          "lastName": "Ram",
          "gender": "male",
          "locality": "Groningen",
          "elected": true
        },
        {
          "id": 34,
          "initials": "P.P.",
          "firstName": "Patrick",
          "lastName": "Crijns",
          "gender": "male",
          "locality": "Landgraaf",
          "elected": true
        },
        {
          "id": 35,
          "initials": "P.",
          "firstName": "Peter",
          "lastName": "Smitskam",
          "gender": "male",
          "locality": "Zoetermeer",
          "elected": true
        },
        {
          "id": 36,
          "initials": "W.",
          "firstName": "Willem",
          "lastName": "Boutkan",
          "gender": "male",
          "locality": "Almere",
          "elected": true
        },
        {
          "id": 37,
          "initials": "F.M.",
          "firstName": "Folkert",
          "lastName": "Thiadens",
          "gender": "male",
          "locality": "Zevenhuizen",
          "elected": true
        },
        {
          "id": 38,
          "initials": "R.B.",
          "firstName": "Reinder",
          "lastName": "Blaauw",
          "gender": "male",
          "locality": "Winschoten",
          "elected": true
        },
        {
          "id": 39,
          "initials": "J.J.T.M.",
          "firstName": "Jan",
          "lastName": "Valize",
          "gender": "male",
          "locality": "Venlo",
          "elected": true
        },
        {
          "id": 40,
          "initials": "C.J.",
          "firstName": "Chris",
          "lastName": "Faddegon",
          "gender": "male",
          "locality": "'s-Gravenhage",
          "elected": true
        },
        {
          "id": 41,
          "initials": "C.N.L.",
          "firstName": "Canice",
          "lastName": "Clermonts",
          "gender": "female",
          "locality": "'s-Gravenhage",
          "elected": true
        },
        {
          "id": 42,
          "initials": "N.A.",
          "firstName": "Nico",
          "lastName": "Uppelschoten",
          "gender": "male",
          "locality": "Eelde",
          "elected": true
        },
        {
          "id": 43,
          "initials": "J.P.",
          "firstName": "Peter",
          "lastName": "Haasen",
          "gender": "male",
          "locality": "Zaandam",
          "elected": true
        },
        {
          "id": 44,
          "initials": "E.R.F.",
          "firstName": "Eric",
          "lastName": "Esser",
          "gender": "male",
          "locality": "'s-Hertogenbosch",
          "elected": true
        },
        {
          "id": 45,
          "initials": "R.J.H.H.",
          "firstName": "Robert",
          "lastName": "Rep",
          "gender": "male",
          "locality": "Ter Apel",
          "elected": true
        },
        {
          "id": 46,
          "initials": "C.H.",
          "firstName": "Cees",
          "lastName": "Riezebos",
          "gender": "male",
          "locality": "Hindeloopen",
          "elected": true
        },
        {
          "id": 47,
          "initials": "S.M.",
          "firstName": "Sylvia",
          "lastName": "Grobben",
          "gender": "female",
          "locality": "Delft",
          "elected": true
        },
        {
          "id": 48,
          "initials": "B.J.M.H.",
          "firstName": "Bart",
          "lastName": "Megens",
          "gender": "male",
          "locality": "Bergen (L)",
          "elected": true
        },
        {
          "id": 49,
          "initials": "W.J.",
          "firstName": "Wim",
          "lastName": "Ubaghs",
          "gender": "male",
          "locality": "Maarssen",
          "elected": true
        },
        {
          "id": 50,
          "initials": "A.G.A.",
          "firstName": "Alberto",
          "lastName": "Blömer",
          "gender": "male",
          "locality": "Enschede",
          "elected": true
        },
        {
          "id": 51,
          "initials": "M.",
          "firstName": "Anita",
          "lastName": "Verbaarschot-Hendriks",
          "gender": "female",
          "locality": "Andel",
          "elected": true
        },
        {
          "id": 52,
          "initials": "J.L.",
          "firstName": "Job",
          "lastName": "Graaf",
          "gender": "male",
          "locality": "Enschede",
          "elected": true
        },
        {
          "id": 53,
          "initials": "F.",
          "firstName": "Frank",
          "lastName": "Feeburg",
          "gender": "male",
          "locality": "Julianadorp",
          "elected": true
        },
        {
          "id": 54,
          "initials": "S.A.G.",
          "firstName": "Serge",
          "lastName": "Ritzen",
          "gender": "male",
          "locality": "Landgraaf",
          "elected": true
        },
        {
          "id": 55,
          "initials": "D.",
          "firstName": "Daniël",
          "lastName": "Moel",
          "gender": "male",
          "locality": "Vijfhuizen",
          "elected": true
        },
        {
          "id": 56,
          "initials": "P.",
          "firstName": "Pieter",
          "lastName": "Boer",
          "gender": "male",
          "locality": "Urk",
          "elected": true
        },
        {
          "id": 57,
          "initials": "J.",
          "firstName": "Jeroen",
          "lastName": "Ahrens",
          "gender": "male",
          "locality": "Vriezenveen",
          "elected": true
        },
        {
          "id": 58,
          "initials": "B.M.",
          "firstName": "Bjorn",
          "lastName": "Roos",
          "gender": "male",
          "locality": "Hellevoetsluis",
          "elected": true
        },
        {
          "id": 59,
          "initials": "S.",
          "firstName": "Stefan",
          "lastName": "Smits",
          "gender": "male",
          "locality": "Zoetermeer",
          "elected": true
        },
        {
          "id": 60,
          "initials": "A.A.A.",
          "firstName": "Alexander",
          "lastName": "Casteleijn",
          "gender": "male",
          "locality": "Katwijk",
          "elected": false
        },
        {
          "id": 61,
          "initials": "H.M.",
          "firstName": "Roel",
          "lastName": "Bijnen",
          "gender": "male",
          "locality": "Ulestraten",
          "elected": false
        },
        {
          "id": 62,
          "initials": "P.",
          "firstName": "Peter",
          "lastName": "Valderen",
          "gender": "male",
          "locality": "Philippine",
          "elected": false
        },
        {
          "id": 63,
          "initials": "M.",
          "firstName": "Menno",
          "lastName": "Vries",
          "gender": "male",
          "locality": "Arum",
          "elected": false
        },
        {
          "id": 64,
          "initials": "D.D.W.",
          "firstName": "Daryl",
          "lastName": "Pfoster",
          "gender": "male",
          "locality": "Sittard",
          "elected": false
        },
        {
          "id": 65,
          "initials": "J.",
          "firstName": "Jorrit",
          "lastName": "Griesen",
          "gender": "male",
          "locality": "Hengelo (O)",
          "elected": false
        },
        {
          "id": 66,
          "initials": "I.F.",
          "firstName": "India",
          "lastName": "Zandman",
          "gender": "female",
          "locality": "Sas van Gent",
          "elected": false
        },
        {
          "id": 67,
          "initials": "S.A.M.",
          "firstName": "Saskia",
          "lastName": "Alberts",
          "gender": "female",
          "locality": "Dordrecht",
          "elected": false
        },
        {
          "id": 68,
          "initials": "K.A.",
          "firstName": "Kelly",
          "lastName": "Blauw",
          "gender": "female",
          "locality": "Groningen",
          "elected": false
        },
        {
          "id": 69,
          "initials": "M.H.J.",
          "firstName": "Thijs",
          "lastName": "Klaassen",
          "gender": "male",
          "locality": "Geleen",
          "elected": false
        },
        {
          "id": 70,
          "initials": "C.B.G.",
          "firstName": "Christian",
          "lastName": "Benerink",
          "gender": "male",
          "locality": "Hengelo (O)",
          "elected": false
        },
        {
          "id": 71,
          "initials": "A.",
          "firstName": "Ashwin",
          "lastName": "Stormbroek",
          "gender": "male",
          "locality": "Lelystad",
          "elected": false
        },
        {
          "id": 72,
          "initials": "N.",
          "firstName": "Natasja",
          "lastName": "Cornelisse",
          "gender": "female",
          "locality": "Assendelft",
          "elected": false
        },
        {
          "id": 73,
          "initials": "S.",
          "firstName": "Serena",
          "lastName": "Liesveld",
          "gender": "female",
          "locality": "Mijdrecht",
          "elected": false
        },
        {
          "id": 74,
          "initials": "N.A.",
          "firstName": "Nathalie",
          "lastName": "Choenni",
          "gender": "female",
          "locality": "Kaatsheuvel",
          "elected": false
        },
        {
          "id": 75,
          "initials": "A.J.M.",
          "firstName": "Ton",
          "lastName": "Kesteren",
          "gender": "male",
          "locality": "Groningen",
          "elected": false
        },
        {
          "id": 76,
          "initials": "I.A.",
          "firstName": "Ilse",
          "lastName": "Bezaan",
          "gender": "female",
          "locality": "Blaricum",
          "elected": false
        },
        {
          "id": 77,
          "initials": "R.M.",
          "firstName": "Rachel",
          "lastName": "Blom",
          "gender": "female",
          "locality": "Rhoon",
          "elected": false
        },
        {
          "id": 78,
          "initials": "M.",
          "firstName": "Marieke",
          "lastName": "Ehlers",
          "gender": "female",
          "locality": "Overijse",
          "elected": false
        },
        {
          "id": 79,
          "initials": "A.W.J.A.",
          "firstName": "Alexander",
          "lastName": "Hattem",
          "gender": "male",
          "locality": "Vinkel",
          "elected": false
        },
        {
          "id": 80,
          "initials": "B.",
          "firstName": "Barry",
          "lastName": "Madlener",
          "gender": "male",
          "locality": "Rockanje",
          "elected": false
        }
      ]
    }

    const transformNameToImageName = (name: string): string => {
      return name.replace('/', '-')
    }

    // returns the slug of the party's leader
    const getPartyLeaderSlug = (party: IParty): string => {
      const candidate = party.candidates[0];

      return candidate.initials + " " + candidate.lastName;
    }

    return {
      party,
      transformNameToImageName,
      getPartyLeaderSlug
    }
  }
})
</script>

<template>
  <router-link
      class="p-8 h-12 flex items-center"
      :to="`/parties`"
  >
    <IconArrow class="h-8 rotate-[270deg]" />
    <p class="font-bold text-lg">Go Back</p>
  </router-link>
  <div class="p-6 max-w-3xl mx-auto">
    <div v-if="party" class="space-y-8">

      <!-- Header -->
      <div class="text-center">
        <h1 class="text-3xl font-bold mb-2">{{ party.name }}</h1>
        <img
            :src="`/logo's/${transformNameToImageName(party.name)}.png`"
            :alt="`${party.name} logo`"
            class="mx-auto w-40 h-40 object-contain"
        />
      </div>

      <!-- Leader -->
      <section class="text-center">
        <h2 class="text-lg text-gray-500">Party Leader</h2>
        <p class="text-xl font-semibold mt-1">
          {{ getPartyLeaderSlug(party) }}
        </p>
      </section>

      <section class="text-center text-gray-500">
        In later versions, there will be more information about the party. Like a description of the party, amount of seats, etc.
      </section>

      <!-- Candidates -->
      <section>
        <h2 class="text-2xl font-bold mb-4">Candidates</h2>

        <ul class="space-y-2">
          <li
              v-for="candidate in party.candidates"
              :key="candidate.id"
              class="p-3 rounded-xl bg-gray-100 shadow-sm flex justify-between items-center"
          >
            <p class="text-gray-700 font-medium">{{ candidate.initials + " " + candidate.lastName }}</p>
            <p v-if="candidate.elected" class="text-green-500 font-semibold">Elected</p>
          </li>
        </ul>
      </section>

    </div>

    <div v-else class="text-center text-xl text-gray-500 mt-10">
      Loading party data...
    </div>
  </div>
</template>


<style scoped>

</style>