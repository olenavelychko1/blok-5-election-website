import {ElectionResultsService} from "@/services/electionResultsService"
import {IResultFilters} from "@/interfaces/IResultFilters"

const electionResultsService = new ElectionResultsService()

export default {
    data() {
        return {
            loadingFilters: false,
            error: null as string | null,
        }
    },

    methods: {
        /**
         * Loads the list of available filter options depending on the selected level.
         */
        async loadFilterOptions(
            filters: IResultFilters,
            targetLevel?: 'Constituency' | 'Municipality' | 'Polling station'
        ) {
            const levelToLoad = targetLevel || filters.level
            if (!levelToLoad) return { options: [] }

            this.loadingFilters = true
            this.error = null

            try {
                let options = []
                switch (levelToLoad) {
                    case 'Constituency':
                        options = await electionResultsService.fetchFilterOptions('Constituency', filters)
                        break
                    case 'Municipality':
                        if (!filters.province) return { options: [] }
                        options = await electionResultsService.fetchFilterOptions('Municipality', filters)
                        break
                    case 'Polling station':
                        if (!filters.authority) return { options: [] }
                        options = await electionResultsService.fetchFilterOptions('Polling station', filters)
                        break
                }
                // console.log(options)
                return { options }
            } catch (err) {
                console.error(err)
                this.error = "Failed to load region filters"
                return { options: [] }
            } finally {
                this.loadingFilters = false
            }
        },
    },
}