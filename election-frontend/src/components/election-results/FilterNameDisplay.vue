<script lang="ts">
import { defineComponent, computed } from 'vue'

export default defineComponent({
  name: 'FilterNameDisplay',
  props: {
    filterNames: {
      type: Object as () => { province?: string; authority?: string; station?: string },
      required: true
    }
  },
  computed: {
    /**
     * Determines the deepest selected level in the region hierarchy.
     *
     * This computed property checks the selected filter names and returns which
     * level is currently active. Used for conditional UI styling, such as bolding
     * the currently selected region in breadcrumb-like displays.
     *
     * @returns 'station' | 'authority' | 'province' | null (The deepest selected level, or null if nothing is selected.)
     */
    deepestLevel(): 'station' | 'authority' | 'province' | null {
      if (this.filterNames.station) return 'station'
      if (this.filterNames.authority) return 'authority'
      if (this.filterNames.province) return 'province'
      return null
    },
    /**
     * Extracts and formats a polling station name and postcode for display.
     *
     * The API returns polling station names in formats such as:
     * "Stembureau Middin Waldeck (postcode: 2551 WK)"
     *
     * This method performs three tasks:
     * 1. Extracts the postcode (Dutch format 1234AB / 1234 AB)
     * 2. Removes the postcode section from the display name
     * 3. Cleans duplicated prefixes (e.g. "Stembureau Stembureau")
     *
     * @returns { name: string, postcode?: string } | null
     *
     * A cleaned object with the station name and (optional) postcode,
     * or null if no station is selected.
     */
    formattedStation(): { name: string; postcode?: string } | null {
      const station = this.filterNames.station
      if (!station) return null

      // Match Dutch postcode pattern like "1234 AB" or "2551 WK"
      const match = station.match(/\b(\d{4}\s?[A-Z]{2})\b/i)
      const postcode = match ? match[1].replace(/\s/g, '') : undefined

      // Remove the postcode and parentheses from the name for display
      let name = station.replace(/\(postcode:\s*\d{4}\s*[A-Z]{2}\)/i, '').trim()
      name = name.replace(/\s*\(\s*\)/g, '') // clean up empty parentheses

      // Remove duplicated “Stembureau Stembureau,” only if it's duplicate (by using \1)
      name = name.replace(/\b(Stembureau)\s+\1\b/i, '$1')

      return { name, postcode }
    }
  }
})
</script>

<template>
  <p class="text-white mx-2">
    <template v-if="filterNames.province">
      <span
          class="mx-1"
          :class="{ 'font-bold': deepestLevel === 'province', 'font-thin': deepestLevel !== 'province' }"
      >
        {{ filterNames.province }}
      </span>
    </template>

    <template v-if="filterNames.authority">
      •
      <span
          class="mx-1"
          :class="{ 'font-bold': deepestLevel === 'authority', 'font-thin': deepestLevel !== 'authority' }"
      >
         {{ filterNames.authority }}
      </span>
    </template>

    <template v-if="formattedStation">
      <span class="text-gray-300">•</span>
      <span
          class="mx-1 inline-flex items-center gap-2"
          :class="{ 'font-bold': deepestLevel === 'station', 'font-thin': deepestLevel !== 'station' }"
      >
        {{ formattedStation.name }}
        <span
            v-if="formattedStation.postcode"
            class="bg-blue-500 text-white text-sm font-semibold px-2 py-0.5 rounded-lg"
        >
          {{ formattedStation.postcode }}
        </span>
      </span>
    </template>
  </p>
</template>
