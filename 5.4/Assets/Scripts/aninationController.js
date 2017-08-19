#pragma strict

var frameNumber:int = 3;
var destroy:boolean = false;

private var index:int = 0;
private var frameRate:float = 0;
private var myTime:float = 0;
private var myIndex:int = 0;

function Start () {
frameRate = 1.0f/frameNumber;
}

function Update () {
myTime += Time.deltaTime;
myIndex = myTime*frameNumber;
index = myIndex % frameNumber;

this.GetComponent.<Renderer>().material.mainTextureScale = new Vector2(frameRate,1);
this.GetComponent.<Renderer>().material.mainTextureOffset = new Vector2(index*frameRate,0);

if (index == frameNumber-1 && destroy ) 
	Destroy(gameObject);
    }