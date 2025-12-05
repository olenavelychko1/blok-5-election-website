<script lang="ts">
import {defineComponent} from 'vue'
import IconAscending from "@/components/icons/IconAscending.vue";
import IconDescending from "@/components/icons/IconDescending.vue";

export default defineComponent({
  name: "ComparisonSortMenu",
  components: {IconDescending, IconAscending},
  props: {
    open: {
      type: Boolean,
      required: true
    },
    sortDesc: {
      type: Boolean,
      required: true
    }
  },
  methods: {
    /**
     * Emit the selected sorting order to the parent component (ComparisonSorter)
     * @param desc
     */
    select(desc: boolean) {
      this.$emit('select', desc)
    },
    /**
     * Handle clicks outside the menu to close it
     * @param event - MouseEvent
     */
    handleClickOutside(event: MouseEvent) {
      if (this.open && this.$refs.menu // make sure menu ref exists
          && !(this.$refs.menu as HTMLElement).contains(event.target as Node) // click outside menu
          && !(event.target as HTMLElement).closest('button')) { // click is not on the toggle button
        this.$emit('update:open', false)
      }
    }
  },
  watch: {
    open(val) {
      if (val) {
        document.addEventListener('mousedown', this.handleClickOutside)
      } else {
        document.removeEventListener('mousedown', this.handleClickOutside)
      }
    }
  }
})
</script>

<template>
  <div v-if="open" ref="menu" class="bg-light border-1 border-gray-blue rounded-sm absolute w-full right-0 top-10 z-10
     flex justify-center flex-col items-end gap-2">
    <button class="text-gray-blue hover:bg-light-blue w-full py-2 px-2 rounded-sm flex justify-end"
            @click.stop="select(true)">
          <span class="text-sm flex flex-row justify-between w-full" :class="{'font-bold': sortDesc}">
            <IconDescending class="w-5"/>
            Descending
          </span>
    </button>
    <button class="text-gray-blue hover:bg-light-blue w-full py-2 px-2 rounded-sm flex justify-end"
            @click.stop="select(false)">
          <span class="text-sm flex flex-row justify-between w-full" :class="{'font-bold': !sortDesc}">
            <IconAscending class="w-5"/>
            Ascending
          </span>
    </button>
  </div>
</template>
