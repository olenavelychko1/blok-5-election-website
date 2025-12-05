import {RegionType} from "@/enums/RegionType";
import {IMetadata} from "@/interfaces/IMetadata";
import {ref, Ref} from "vue";
import {IMetadataService} from "@/interfaces/IMetadataService";
import {MetadataService} from "@/services/MetadataService";

export async function useMetadata(regionType: RegionType, regionId: string): Promise<{ metadata: Ref<IMetadata>}> {
    const metadata: Ref<IMetadata> = ref<IMetadata>();
    const metadataService: IMetadataService = new MetadataService();
    metadata.value = await metadataService.getByRegionTypeAndId(regionType, regionId);
    return {metadata};
}