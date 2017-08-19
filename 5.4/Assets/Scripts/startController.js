var startTexture : Texture2D;
//var LoadGame ; Texture2D;
var buttonA:Texture2D;
var buttonB:Texture2D;

function Update ()
{

}

function OnGUI()
{
	GUI.DrawTexture(Rect(0,0,Screen.width,Screen.height),startTexture);

	GUI.Label(Rect(662,274,100,100)," ");
	GUI.Label(Rect(662,355,200,200)," ");
	GUI.DrawTexture(Rect(662,294,64,64),buttonA);
	GUI.DrawTexture(Rect(662,378,64,64),buttonB);
}