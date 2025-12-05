<script lang="ts">
import {defineComponent} from 'vue'
import IconEmail from "@/components/icons/IconEmail.vue";
import IconPhone from "@/components/icons/IconPhone.vue";

export default defineComponent({
  name: "FooterContact",
  components: {IconPhone, IconEmail}
})
</script>

<template>
  <div class="flex flex-col w-full xl:w-1/3 mt-5 xl:mt-0">
    <h3 class="text-lg text-green text-left">Contact</h3>
    <p class="w-full xl:w-2/3 text-left text-light mt-1">
      For inquiries, support, or feedback, please reach out to us at:
    </p>
    <p class="w-full xl:w-1/3 text-left text-light mt-2 flex flex-row gap-2">
      <IconEmail class="text-light w-5"/>
      elections@hva.nl
    </p>
    <p class="w-full xl:w-1/3 text-left text-light mt-1 flex flex-row gap-2">
      <IconPhone class="text-light w-5"/>
      + 31 1234567890
    </p>
  </div>
</template>
