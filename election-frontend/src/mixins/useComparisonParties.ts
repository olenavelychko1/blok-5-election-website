import PartyService from "@/services/partyService.js";

const service = new PartyService()

/**
 * Mixin used for fetching the parties on the comparison page.
 */
export default {
    data() {
        return {
            parties: [],
            loading: false,
            error: null,
            filter: {
                page: 1,
                size: 100,
                sort: [{
                    property: 'id',
                    direction: 'asc'
                }],
                electionId: "TK2025",
            },
        }
    },
    methods: {
        async fetchParties() {
            this.loading = true;
            this.error = null;
            try {
                const {data} = await service.getParties(this.filter)
                this.parties = data;
            } catch (err) {
                this.error = err?.message ?? 'An error occurred while fetching parties.';
            } finally {
                this.loading = false;
            }
        }
    },
    created() {
        this.fetchParties();
    }
}