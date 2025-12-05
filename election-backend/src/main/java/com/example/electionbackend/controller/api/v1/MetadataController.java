package com.example.electionbackend.controller.api.v1;

import com.example.electionbackend.interfaces.IMetadataService;
import com.example.electionbackend.model.Metadata;
import com.example.electionbackend.type.RegionType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controller for handling Metadata related endpoints.
 */
@RestController
@RequestMapping("/v1/metadata")
public class MetadataController {
    private IMetadataService metadataService;

    /**
     * Constructor for MetadataController.
     *
     * @param metadataService - the metadata service
     */
    public MetadataController(IMetadataService metadataService) {
        this.metadataService = metadataService;
    }

    /**
     * Get Metadata by Region Type and ID
     *
     * @param id         - the ID of the region
     * @param regionType - the type of the region
     * @return - the list of Metadata associated with the Region Type and ID
     */
    @GetMapping("/region/{regionType}/{id}")
    public ResponseEntity<List<Metadata>> getByRegionTypeAndId(@PathVariable String id, @PathVariable RegionType regionType) {
        try {
            List<Metadata> metadata = metadataService.getByRegionTypeAndId(regionType, id);
            if (metadata == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(metadata);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Get All Metadata by Region Type
     *
     * @param regionType - the type of the region
     * @return - the list of Metadata associated with the Region Type
     */
    @GetMapping("/region/{regionType}")
    public ResponseEntity<List<Metadata>> getByRegionType(@PathVariable RegionType regionType) {
        try {
            List<Metadata> metadata = metadataService.getByRegionType(regionType);
            if (metadata == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(metadata);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}