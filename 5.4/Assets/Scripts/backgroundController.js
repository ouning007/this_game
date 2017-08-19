var speed:float = 2.0f;
private var change:boolean=false;

function Update()
{
	transform.Translate(Vector3.left*speed* Time.deltaTime);
	
	if(transform.position.x<= -11.4)
	{
		transform.position.x = 11.4;
		change=true;
	}
	if(transform.position.x<0.5 && change)
	{
		transform.position.x=0.5;
		change=false;
	}	
}