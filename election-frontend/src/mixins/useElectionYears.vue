<script lang="ts">
import {defineComponent} from 'vue'
import {ElectionResultsService} from "@/services/electionResultsService";

const electionResultsService = new ElectionResultsService()

export default defineComponent({
  name: "useElectionYears",
  data() {
    return {
      loadingYears: false,
      errorYears: null as string | null,
    }
  },
  methods: {
    /**
     * Loads the list of available election years.
     * Each item contains the election id/year (TK2025) and state ID (e.g., 1, 2, 3).
     */
    async loadElectionYears() {
      this.loadingYears = true
      this.errorYears = null

      try {
        const electionYears = await electionResultsService.fetchElectionYears()
        return { electionYears }
      } catch (err) {
        this.errorYears = "Failed to load election years"
        return { electionYears: [] }
      } finally {
        this.loadingYears = false
      }
    }
  }
})
</script>
