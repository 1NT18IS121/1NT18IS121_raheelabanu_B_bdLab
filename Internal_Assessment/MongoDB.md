1. Demonstrate the usage of $match, $group, aggregate pipelines.

	(a) $match : It selects the recods that matches the specified condition
  
		db.movies.aggregate([{ $match : { Genere : "Animation" }}]).pretty()
    
	(b) $group : It generally groups the input based on a specified id.
  
		db.movies.aggregate([{ $group : (_id: "$Genere" , count: {$sum:1}}}])
    
	(c) aggregate pipelines :
  
		db.movies.aggregate([{$match:{"Number of Positive Feedbacks":{$gt:4000}}},{$group:{_id:"$Genere",count:{$sum:1}}}]).pretty()

2. Demonstrate the Map-Reduce aggregate function on this dataset.

       var mapFunc = function(){emit(this.Genere, this["IMDB rating"]);};

       var reducerFuc = function(genere,rating){return Array.avg(rating);};

       db.movies.mapReduce(mapFunc,reducerFunc, {out: "Output"});
  
3. Count the number of Movies which belong to the thriller category and find out the total number of positive reviews in that category. 

	    db.movies.aggregate([[$match:{"Genere":"Thriller"}},{$group:{_id:"$Genere",count:{$sum:1},value:{$sum:"$Number of Positive Feedbacks"}}}]).pretty()

4. Group all the records by genre and find out the total number of positive feedbacks by genre.
	
	    db.movies.aggregate([{$group:{_id:"$Genere",count:{$sum:1},Total number of Positive Feedbacks:{$sum:"Number of Positive Feedbacks"}}}]).pretty()
