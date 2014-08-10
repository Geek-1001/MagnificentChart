#MagnificentChart

Simple open source Android library for round charts.

<img src="https://www.dropbox.com/s/zkk8s3uq0ht0f1j/Untitled.gif?dl=1" height="60%" width="60%">

<img src="https://www.dropbox.com/s/53uiojut8bf6m0q/magnificent_chart_1.png?dl=1" height="60%" width="60%">

##Usage:

###Step 1

declare MagnificentChart in your xml layout

	<MagnificentChart
        android:layout_width="250dp"
        android:layout_height="250dp"
        app:animation="false"
        app:round="false"
        app:shadow="true"
        app:shadowColor="#F2F2F2"
        app:background="#fff"
        android:id="@+id/magnificentChart" />
        
       
###Step 2

Find object in your Activity/Fragment 

	MagnificentChart magnificentChart = (MagnificentChart) findViewById(R.id.magnificentChart);
	
	        
###Step 3

Create segments for diagram	 and set them to List\<MagnificentChartItem>

	MagnificentChartItem firstItem = new  MagnificentChartItem("first", 23, Color.parseColor("#BAF0A2"));
	List<MagnificentChartItem> chartItemsList = new ArrayList<MagnificentChartItem>();
	chartItemsList.add(firstItem);
	
###Step 4	

Set chart items to MagnificentChart object.
And set max value for chart.

	magnificentChart.setChartItemsList(chartItemsList);
	magnificentChart.setMaxValue(100);
	
###Step 5

Enjoy!


##Implementation:

###Step 1

Copy **MagnificentCgart** and **MagnificentChartItem** classes into your project. 	
###Step 2	
	
Change packages name.

###Step 3	
	
In **/value/attr** file 

	<declare-styleable name="MagnificentChart">
        <attr name="animation" format="boolean"/>
        <attr name="round" format="boolean" />
        <attr name="shadow" format="boolean" />
        <attr name="shadowColor" format="color" />
        <attr name="background" format="color" />
    </declare-styleable>
    
###Step 4

Enjoy!     
	