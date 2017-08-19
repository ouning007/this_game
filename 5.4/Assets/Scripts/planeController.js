#pragma strict

var speed:float = 2.0f;
var bome:Rigidbody;

//private var myBomb:Rigidbody;

function Start () {

}

function Update () {
	if(Input.GetAxis("Horizontal")>0)
		transform.Translate(Vector3.right*speed*Time.deltaTime);
    if(Input.GetAxis("Horizontal")<0)
		transform.Translate(2*Vector3.left*speed*Time.deltaTime);
	transform.Translate(Input.GetAxis("Vertical")*Vector3.up*speed*Time.deltaTime);
	
	if(Input.GetButtonDown("Jump"))
	{
	Instantiate(bome,transform.position+Vector2(0,-0.05),
	         transform.rotation);	
	
/*	if(Input.GetAxis("Horizontal")>0)
		myBomb.velocity.x=1.5*speed;
	else if(Input.GetAxis("Horizontal")==0)
		myBomb.velocity.x=0;
	else
		myBomb.velocity.x=-1.5*speed;
		*/
	}

}