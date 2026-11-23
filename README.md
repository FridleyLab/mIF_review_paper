# Comparative case study: processing of multiplexed immunofluorescence tissue images

Code to reproduce the comparative case study (FIG. 3 and FIG. 4) in Sabitov et al., "Processing of multiplexed immunofluorescence tissue images".

Data: https://doi.org/10.5281/zenodo.24454389

## Source image

Open-access CODEX image of breast ductal carcinoma NOS from HTAN (HT206B1-S1H2L4__20221028.ome.tiff; data file ID [HTA12_129_…], Synapse syn53271341), available through the [HTAN Data Portal](https://humantumoratlas.org). Channels used: DAPI, CD3e, CD8, CD68.

## Repository structure

```
qupath/
  stardist_segmentation.groovy    StarDist, all parameters in the script
  watershed_segmentation.groovy   QuPath watershed, all parameters in the script
  dsb2018_heavy_augment.pb        StarDist model (from qupath/models)
analysis/
  mIF_comparative_study.Rmd       full analysis
  session_info.txt                R environment details and package versions
```

Segmentation outputs (cell tables, label masks, cell outlines and the analyzed region) are on Zenodo, not here.

## Reproducing

**Segmentation** (optional, outputs already on Zenodo): in QuPath 0.6.0 open the source image, import the annotation from either GeoJSON on Zenodo, copy the model file into the project folder, and run each script in `qupath/`. Both segment nuclei on DAPI and expand them to cells, by 3 µm (StarDist) and 5 µm (watershed).

**Analysis:**

1. Clone the tagged release.
2. In R (≥ 4.4), install the two GitHub packages at the commits used:
   ```r
   remotes::install_github("JiangmeiRubyXiong/cfGMM@47b5b6f8b6cecb05aa6bca66972e793d8b32cd86")
   remotes::install_github("JiangmeiRubyXiong/GammaGateR@72f59d55b8558f7f3bb4230347749951f629ed12")
   ```
   Install the other packages loaded in the `setup` chunk from CRAN and Bioconductor (FlowSOM). Versions used are listed in `analysis/session_info.txt`.
3. Open `analysis/mIF_comparative_study.Rmd` and run all chunks.

The Rmd reads `stardist.txt` and `watershed.txt` directly from the Zenodo record, so no download is needed. It writes the seven figure panels and `agreement_tables.csv` to its own folder. The mask overlays in FIG. 3b come from QuPath, not the Rmd.

## Expected output

The `checks` chunk should show `diff = 0` for every row:

| Quantity | Value |
|---|---|
| StarDist cells | 99,731 |
| Watershed cells | 49,276 |
| Matched cells | 45,842 |
| GammaGateR CD3+ / CD8+ / CD68+ | 14,758 / 5,732 / 2,719 |
| Single-step CD8 Jaccard | 0.794 |

Figures and tables from the submitted run are in `results/` for comparison.

FlowSOM metaclusters are labeled from their printed means. If a different FlowSOM version renumbers them, the run stops with an error, and the maps at the top of the `phenotyping` chunk need updating.

## License

Code: MIT. Data: see the Zenodo record and HTAN data use terms.
