// QuPath watershed cell detection (QuPath [version])
// Run on the source image with the analysis annotation loaded (annotation.geojson).
// Writes watershed.txt to the project folder.

setImageType('FLUORESCENCE')

removeDetections()
selectAnnotations()   // detect inside the annotation only
runPlugin('qupath.imagej.detect.cells.WatershedCellDetection', '''{"detectionImage":"DAPI",
    "requestedPixelSizeMicrons":0.5,
    "backgroundRadiusMicrons":8.0,
    "backgroundByReconstruction":true,
    "medianRadiusMicrons":0.0,
    "sigmaMicrons":1.5,
    "minAreaMicrons":10.0,
    "maxAreaMicrons":400.0,
    "threshold":5.0,
    "watershedPostProcess":true,
    "cellExpansionMicrons":5.0,
    "includeNuclei":true,
    "smoothBoundaries":true,
    "makeMeasurements":true}''')

saveDetectionMeasurements(buildFilePath(PROJECT_BASE_DIR, 'watershed.txt'))
