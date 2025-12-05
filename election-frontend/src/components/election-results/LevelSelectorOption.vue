<script lang="ts">
import {Component, defineAsyncComponent, defineComponent, PropType} from 'vue'
import IconProvince from "@/components/icons/result-filter-panel/IconProvince.vue";
import IconLocalAuthority from "@/components/icons/result-filter-panel/IconLocalAuthority.vue";
import IconPollingStation from "@/components/icons/result-filter-panel/IconPollingStation.vue";
import IconYear from "@/components/icons/result-filter-panel/IconYear.vue";
import {IRegion} from "@/interfaces/IRegion";

/**
 * Reusable dropdown component representing a single level selector (e.g. Province, Municipality).
 *
 * It displays a label with an icon and a corresponding `<select>` element populated from `options`.
 * The icon can be dynamically loaded via the `icon` prop for flexibility.
 */
export default defineComponent({
  name: "LevelSelectorOption",
  components: {
    IconProvince,
    IconLocalAuthority,
    IconPollingStation,
    IconYear,
  },
  props: {
    /** Label text displayed above the select element */
    label: {type: String, required: true},
    /**
     * The name of the icon component to display.
     * Must match the corresponding file name inside the icons directory (without prefix/suffix).
     */
    icon: {type: String, required: true},
    /** The current selected value */
    modelValue: {
      type: [String, Number, Object] as PropType<string | number | IRegion>,
      default: ''
    }
    ,
    /**
     * The available dropdown options. So the list of municipalities/provinces/polling stations.
     * Each object must contain an `id` and `name` property.
     */
    options: {type: Array as () => { id: string | number, name: string }[], default: () => []},
    /** Placeholder text for the dropdown when no value is selected */
    placeholder: {type: String, default: 'Select option'},
    /** Whether the dropdown is disabled */
    disabled: {type: Boolean, default: false},
  },
  emits: ['update:modelValue', 'click', 'change'],
  methods: {
    /**
     * Handles select change events by emitting the new value
     * and triggering optional `change` listeners.
     *
     * @param {Event} event - The native input event.
     */
    handleChange(event: Event) {
      const target = event.target as HTMLSelectElement
      // Updates the v-model value
      this.$emit('update:modelValue', target.value)
      // Updates year filter
      this.$emit('change', event)
    },
  },
  computed: {
    /**
     * Dynamically resolves the icon component based on the `icon` prop. Automatically imports the corresponding file.
     * Falls back to a console warning if the icon is not found.
     *
     * Example: allowed values: 'Province', 'LocalAuthority', 'PollingStation', 'Year'
     *
     * @returns {Component | void} The Vue component for the icon.
     */
    IconComponent(): Component | void {
      if (!this.icon) return console.log(`No icon ${this.icon} found`); // avoids undefined imports
      // Dynamically import matching file based on prop
      return defineAsyncComponent(() =>
          import(`@/components/icons/result-filter-panel/Icon${this.icon}.vue`)
      );
    },
    /**
     * Returns the dropdown options with cleaned names when used for Polling Stations.
     *
     * This method automatically removes only *adjacent duplicated namespace prefixes*
     * (e.g. "Stembureau Stembureau" → "Stembureau"), without affecting legitimate names.
     *
     * @returns Array of cleaned option objects, each with `{ id, name }` or the original options if not a polling station selector.
     */
    cleanedOptions(): { id: string | number, name: string }[] {
      // Only clean names if this is the Polling Station selector
      const isPollingStation = this.label.toLowerCase().includes("polling")
      const isYear = this.label.toLowerCase().includes("year")

      if (!isPollingStation && !isYear) return this.options

      return this.options.map(opt => {
        let name = opt.name
        // Remove duplicated “Stembureau Stembureau,” only if it's duplicate (by using \1)
        if (isPollingStation) {
          name = name.replace(/\b(Stembureau)\s+\1\b/i, "$1")
        }

        // Extract year from "TK2023" format
        if (isYear) {
          name = name.replace(/^TK(\d{4})$/, "$1")
        }
        return { ...opt, name }
      })
    }
  }
})
</script>

<template>
  <div>
    <!-- Label and icon -->
    <label class="mb-1 font-medium flex items-center gap-3">
      <component :is="IconComponent" class="w-5 h-5 flex"/>
      {{ label }}
    </label>

    <!-- Dropdown select -->
    <select
        class="w-full bg-light border p-2 rounded-lg text-gray-blue cursor-pointer"
        :value="modelValue"
        :disabled="disabled"
        @click="$emit('click')"
        @change="handleChange"
    >
      <option v-if="icon !== 'Year'" value="">{{ placeholder }}</option>
      <option v-for="opt in cleanedOptions" :key="opt.id" :value="opt.id">{{ opt.name }}</option>
    </select>
  </div>
</template>

<style scoped>

</style>