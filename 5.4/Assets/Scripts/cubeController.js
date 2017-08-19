#pragma strict

var speed:float = 1;
var project:Rigidbody;

private var amtToRotate:float = 0;
private var isMoving:boolean = true;
private var myProjecttile:Rigidbody;

function Start () 
{
  amtToRotate = 0.1*speed;
  transform.localEulerAngles.z = 325;
}

function Update () 
{
/*
    if(transform.localEulerAngles.z > 300)
    {
    */
    if(isMoving)
    {
        transform.localEulerAngles.z -= amtToRotate;
        var fwd = transform.TransformDirection(Vector3.left);
        
        if(Physics.Raycast(transform.position ,fwd,10))
        {
        myProjecttile = Instantiate(project,transform.position+fwd,
        transform.rotation);
        
        myProjecttile.velocity = fwd*20;
        isMoving = false;
        
        waiteForTime();
        
        }
      }
       transform.localEulerAngles.z = 325;
 /*
    }
else
    transform.localEulerAngles.z = 350;
    */
}
function waiteForTime()
{
    yield WaitForSeconds(1);
    isMoving = true;
}