import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import org.jfree.chart.*;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.StatisticalBarRenderer;
import org.jfree.chart.renderer.xy.StandardXYBarPainter;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.chart.title.LegendTitle;
import org.jfree.chart.title.TextTitle;
import org.jfree.chart.ui.HorizontalAlignment;
import org.jfree.chart.ui.TextAnchor;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.statistics.DefaultStatisticalCategoryDataset;
import org.jfree.data.statistics.HistogramDataset;
import org.jfree.data.statistics.SimpleHistogramDataset;

public class MakeChart {

    public MakeChart() {

    }

    //Pie chart example
    public void createTestPieChart(String title, DefaultPieDataset dataset) {
        JFreeChart chart = ChartFactory.createPieChart(
                title, // chart title
                dataset, // data
                true, // legend
                false, // tooltips
                false // no URL generation
        );

        //Title
        TextTitle t = chart.getTitle();
        t.setHorizontalAlignment(HorizontalAlignment.LEFT);
        t.setPaint(new Color(0, 0, 0));
        t.setFont(new Font("Arial", Font.BOLD, 42));

        //Legend
        LegendTitle l = chart.getLegend();
        l.setItemFont(new Font("Arial", Font.BOLD, 18));

        //Start of pie graph
        PiePlot plot = (PiePlot) chart.getPlot();
        plot.setBackgroundPaint(null);
        plot.setInteriorGap(0.04);
        plot.setOutlineVisible(false);
        plot.setDefaultSectionOutlinePaint(Color.BLACK);
        plot.setSectionOutlinesVisible(true);
        plot.setDefaultSectionOutlineStroke(new BasicStroke(2.0f));

        // customise the section label appearance
        plot.setLabelFont(new Font("Arial", Font.BOLD, 32));
        plot.setLabelLinkPaint(Color.BLACK);
        plot.setLabelLinkStroke(new BasicStroke(2.0f));
        plot.setLabelOutlineStroke(null);
        plot.setLabelPaint(Color.WHITE);
        plot.setLabelBackgroundPaint(Color.GRAY);

        File image = new File("temp.png");
        try {
            ChartUtils.saveChartAsPNG(image, chart, 1800,1000 );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Make same thing as above but working with ratings


    //Bar chart example
    public void createTestBarChart(String title){
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.addValue(10.0, "Row 1", "Column 1");
        dataset.addValue(15.0, "Row 1", "Column 2");
        dataset.addValue(13.0, "Row 1", "Column 3");
        dataset.addValue(7.0, "Row 1", "Column 4");

        dataset.addValue(22.0, "Row 2", "Column 1");
        dataset.addValue(18.0, "Row 2", "Column 2");
        dataset.addValue(28.0, "Row 2", "Column 3");
        dataset.addValue(17.0, "Row 2", "Column 4");

        JFreeChart chart = ChartFactory.createLineChart(title, "Type", "Value", dataset);

        LegendTitle l = chart.getLegend();
        l.setItemFont(new Font("Arial",Font.BOLD, 18));

        CategoryPlot plot = (CategoryPlot) chart.getPlot();

        // customise the range axis...
        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        rangeAxis.setAutoRangeIncludesZero(false);

        // customise the renderer...
        BarRenderer renderer = new BarRenderer();
        renderer.setDrawBarOutline(true);
        renderer.setIncludeBaseInRange(false);
        plot.setRenderer(renderer);

        renderer.setDefaultItemLabelGenerator(new StandardCategoryItemLabelGenerator());
        renderer.setDefaultItemLabelsVisible(true);
        renderer.setDefaultItemLabelPaint(Color.yellow);
        renderer.setDefaultItemLabelFont(new Font("Arial", Font.BOLD,16));
        renderer.setDefaultPositiveItemLabelPosition(new ItemLabelPosition(
                ItemLabelAnchor.INSIDE6, TextAnchor.BOTTOM_CENTER));

        // set up gradient paints for series...
        renderer.setSeriesPaint(0, new Color(0,0,170));
        renderer.setSeriesPaint(1, new Color(0,170,0));

        File image = new File("temp.png");
        try {
            ChartUtils.saveChartAsPNG(image, chart, 1800,1000 );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createBarGraph(String title,DefaultCategoryDataset dataset){
        JFreeChart chart = ChartFactory.createLineChart(title, "Anime", " ", dataset);

        LegendTitle l = chart.getLegend();
        l.setItemFont(new Font("Arial",Font.BOLD, 16));

        CategoryPlot plot = (CategoryPlot) chart.getPlot();

        // customise the range axis...
        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        rangeAxis.setAutoRangeIncludesZero(true);

        // customise the renderer...
        BarRenderer renderer = new BarRenderer();
        renderer.setDrawBarOutline(true);
        renderer.setIncludeBaseInRange(false);
        plot.setRenderer(renderer);

        plot.setWeight(500);

        renderer.setDefaultItemLabelGenerator(new StandardCategoryItemLabelGenerator());
        renderer.setDefaultItemLabelsVisible(true);
        renderer.setDefaultItemLabelPaint(Color.BLACK);
        renderer.setDefaultItemLabelFont(new Font("Arial", Font.BOLD,16));
        renderer.setDefaultPositiveItemLabelPosition(new ItemLabelPosition(
                ItemLabelAnchor.INSIDE6, TextAnchor.BOTTOM_CENTER));

        // set up gradient paints for series...
        //renderer.setSeriesPaint(0, new Color(0,0,170));
        //renderer.setSeriesPaint(1, new Color(0,170,0));

        File image = new File("temp.png");
        try {
            ChartUtils.saveChartAsPNG(image, chart, 1800,1000 );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createHistogram(String title, SimpleHistogramDataset dataset){
        JFreeChart chart = ChartFactory.createHistogram(title, "Ratings", "Amount", dataset,
                PlotOrientation.VERTICAL, true, true, false);

        LegendTitle l = chart.getLegend();
        l.setItemFont(new Font("Arial",Font.BOLD, 16));

        XYPlot plot = (XYPlot) chart.getPlot();
        plot.setDomainPannable(true);
        plot.setRangePannable(true);
        plot.setForegroundAlpha(0.85f);

        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setAutoRangeIncludesZero(true);

        XYBarRenderer renderer = (XYBarRenderer) plot.getRenderer();
        renderer.setDrawBarOutline(true);
        renderer.setBarPainter(new StandardXYBarPainter());
        renderer.setShadowVisible(false);

        File image = new File("temp.png");
        try {
            ChartUtils.saveChartAsPNG(image, chart, 1800,1000 );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
