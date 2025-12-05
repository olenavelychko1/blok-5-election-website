import {IRegion} from "@/interfaces/IRegion";
import {onMounted, Ref, ref} from "vue";
import {IMunicipalityService} from "@/interfaces/IMunicipalityService";
import {MunicipalityService} from "@/services/MunicipalityService";

/**
 * Custom hook to fetch and manage municipalities data.
 * @returns An object containing a reactive reference to an array of municipalities.
 */
export async function useMunicipalities(): Promise<{ municipalities: Ref<IRegion[]> }> {
    const municipalities: Ref<IRegion[]> = ref<IRegion[]>([]);
    const municipalityService: IMunicipalityService = new MunicipalityService();
    municipalities.value = await municipalityService.getAll();
    return {municipalities};
}