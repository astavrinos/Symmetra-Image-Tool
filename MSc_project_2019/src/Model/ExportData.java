package Model;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ExportData extends Model {
	DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss");
	LocalDateTime getTime = LocalDateTime.now();

	String pathToSave = "symmetra_analyzation_" + dateTimeFormat.format(getTime) + ".csv";

	public ExportData(List<ImageDetails> imageDetailsList, List<Calculations> calcImageColors) {

		try {

			FileWriter csvWriter = new FileWriter(System.getProperty("user.home") + "//Desktop//" + pathToSave);			
			csvWriter.append("image_path");
			csvWriter.append(",");
			csvWriter.append("image_name");
			csvWriter.append(",");
			csvWriter.append("image_size");
			csvWriter.append(",");
			csvWriter.append("image_width");
			csvWriter.append(",");
			csvWriter.append("image_height");
			csvWriter.append(",");
			csvWriter.append("area");
			csvWriter.append(",");
			csvWriter.append("gray_values_mean");
			csvWriter.append(",");
			csvWriter.append("median");
			csvWriter.append(",");
			csvWriter.append("variance");
			csvWriter.append(",");
			csvWriter.append("standard_deviation");
			csvWriter.append(",");
			csvWriter.append("skewness");
			csvWriter.append("\n");

			for (int i = 0; i < imageDetailsList.size(); i++) {
				csvWriter.append("" + imageDetailsList.get(i).getImagePath());
				csvWriter.append(",");
				csvWriter.append("" + imageDetailsList.get(i).getImageName());
				csvWriter.append(",");
				csvWriter.append("" + imageDetailsList.get(i).getImageSize());
				csvWriter.append(",");
				csvWriter.append("" + imageDetailsList.get(i).getImageWidth());
				csvWriter.append(",");
				csvWriter.append("" + imageDetailsList.get(i).getImageHeight());
				csvWriter.append(",");
				csvWriter.append("" + calcImageColors.get(i).getPixelsNumber());
				csvWriter.append(",");
				csvWriter.append("" + calcImageColors.get(i).getMeanGrayValueResult());
				csvWriter.append(",");
				csvWriter.append("" + calcImageColors.get(i).getMedianResult());
				csvWriter.append(",");
				csvWriter.append("" + calcImageColors.get(i).getVarianceResult());
				csvWriter.append(",");
				csvWriter.append("" + calcImageColors.get(i).getStdDeviationResult());
				csvWriter.append(",");
				csvWriter.append("" + calcImageColors.get(i).getSkewnessResult());
				csvWriter.append("\n");
			}

			csvWriter.flush();
			csvWriter.close();
		} catch (IOException a) {
			a.printStackTrace();
		}
	}

	public String getPathToSave() {
		return pathToSave;
	}

}
