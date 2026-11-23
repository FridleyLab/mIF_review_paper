// StarDist nuclear segmentation with cell expansion (QuPath [version], StarDist extension [version])
// Run on the source image with the analysis annotation loaded (annotation.geojson).
// Writes stardist.txt to the project folder.

import qupath.ext.stardist.StarDist2D

setImageType('FLUORESCENCE')

// Pretrained model: dsb2018_heavy_augment.pb, from https://github.com/qupath/models (stardist folder)
def modelPath = buildFilePath(PROJECT_BASE_DIR, 'dsb2018_heavy_augment.pb')

def stardist = StarDist2D.builder(modelPath)
    .channels('DAPI')
    .normalizePercentiles(1, 99.8)   // per-tile percentile normalization of DAPI
    .threshold(0.3)                  // detection probability threshold
    .pixelSize(getCurrentServer().getPixelCalibration().getPixelWidthMicrons())  // native, 0.510 µm
    .cellExpansion(3)                // µm
    .measureShape()
    .measureIntensity()
    .build()

removeDetections()
stardist.detectObjects(getCurrentImageData(), getAnnotationObjects())   // inside the annotation only

saveDetectionMeasurements(buildFilePath(PROJECT_BASE_DIR, 'stardist.txt'))
