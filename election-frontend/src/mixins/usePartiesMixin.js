import PartyService from "@/services/partyService.js"

const service = new PartyService()

/**
 * This mixin is used to fetch and handle party data.
 */
export default {
    // Define the data property with an empty array
    data() {
        return {
            parties: [],
            loading: false,
            totalPartiesCountLoading: false,
            totalPartiesCount: 0,
            error: null,
            filter: {
                page: 1,
                size: 10,
                sort: [{
                    property: 'id',
                    direction: 'asc'
                }],
                electionId: "TK2025",
            },
            ignorePageWatch: false,
        }
    },

    // Define the method property with the fetchParties method
    methods: {
        async fetchParties() {
            const oldParties = [...this.parties];
            this.loading = true;


            this.error = null
            try {
                const {data} = await service.getParties(this.filter)
                const newParties = Array.isArray(data) ? data : [];

                if (oldParties.length !== newParties.length ||
                    oldParties.some((p, i) => p.id !== newParties[i]?.id)) {
                    this.parties = newParties
                }
            } catch (e) {
                this.error = e.message;
            } finally {
                this.loading = false;
            }
        },

        async fetchTotalPartiesCount() {
            this.totalPartiesCountLoading = true;

            this.error = null
            try {
                const { data } = await service.getTotalPartiesCount(this.filter.electionId)
                this.totalPartiesCount = data;
            } catch (e) {
                this.error = e.message;
            } finally {
                this.totalPartiesCountLoading = false;
            }
        }
    },

    watch: {
        'filter.page'(newVal, oldVal) {
            if (newVal === oldVal || this.ignorePageWatch) {
                return
            }

            this.loading = true;
            window.scrollTo({ top: 0, behavior: "smooth" })
            void this.fetchParties()
        },
        'filter.size'(newVal, oldVal) {
            if (newVal === oldVal) {
                return
            }

            // Calculate index of the first item shown on the current page
            const firstItemIndex = (this.filter.page - 1) * oldVal + 1;

            // Determine what the new page should be so that the same item stays visible
            const newPage = Math.floor((firstItemIndex - 1) / newVal) + 1;

            // Compute max page in case total items shrink
            const maxPage = Math.ceil(this.totalPartiesCount / newVal);

            // Clamp page between 1 and maxPage
            const adjustedPage = Math.min(newPage, maxPage);

            if (adjustedPage !== this.filter.page) {
                this.ignorePageWatch = true;
                this.filter.page = adjustedPage;
                this.ignorePageWatch = false;

            }

            this.loading = true;
            window.scrollTo({ top: 0, behavior: "smooth" })
            void this.fetchParties()
        },
        'filter.electionId'(newVal, oldVal) {
            if (newVal === oldVal) {
                return
            }

            this.ignorePageWatch = true;
            this.filter.page = 1;
            this.ignorePageWatch = false;

            this.loading = true;
            void this.fetchTotalPartiesCount();
            window.scrollTo({ top: 0, behavior: "smooth" })
            void this.fetchParties()
        },
        'filter.sort'(newVal, oldVal) {
            if (newVal === oldVal) {
                return
            }

            this.ignorePageWatch = true;
            this.ignorePageWatch = false;

            this.loading = true;
            window.scrollTo({ top: 0, behavior: "smooth" })
            void this.fetchParties()
        }
    },

    // Call the fetchParties method when the component is created
    created() {
        void this.fetchParties();
        void this.fetchTotalPartiesCount();
    }
}
