var PNG = require('png-js');
var image = PNG.load('3.png');

image.decode(function (pixels) {
	console.log(pixels.length);
	var isWhite = function (i, j) {
		var index =  image.width * i + j;
		return (pixels[4 * index] == 255);
	};

	var midPointI;
	var midPointJ;
	for (var i = 0; i < image.height; i++) {
		var rowStart;
		var rowEnd;
		for (var j = 0; j < image.width; j++) {
			var lastPixel = pixel;
			var pixel = isWhite(i, j);
			if (!pixel && lastPixel) {
				rowStart = j;
			} else if (pixel && !lastPixel) {
				rowEnd = j;
				console.log("found something " + i + " " + j);
				break;
			}
		}
		if (rowEnd && rowStart) {
			midPointJ = Math.floor((rowEnd + rowStart)/2);
			midPointI = i;
			break;
		}
	}
		
	for (i = midPointI; i < image.height && !isWhite(i, midPointJ); i++) {}
	console.log("found midI to be" + midPointJ);

	var diameter = i - midPointI;
	
	var leftEdge = Math.max(0, midPointJ - Math.floor(diameter/2));
	var rightEdge = Math.min(image.width, midPointJ + Math.ceil(diameter/2));
	var upEdge = Math.max(0, midPointI);
	var downEdge = Math.min(image.height, midPointI + diameter);
	
	var area = 0;
	for (i = upEdge; i < downEdge; i++) {
		for (j = midPointJ; j < rightEdge; j++) {
			if (!isWhite(i, j)) {
				area++;
			} else {
				break;
			}
		}
		
		for (j = midPointJ - 1; j > leftEdge; j--) {
			if (!isWhite(i, j)) {
				area++;
			} else {
				break;
			}
		}
	}
	
	var pi = area/Math.pow(diameter/2, 2);
	console.log(Math.abs(Math.PI - pi) / Math.PI);
});