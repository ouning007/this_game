using UnityEngine;
using System.Collections;

public class tankController : MonoBehaviour {
     
	 public  float currentspeed = 0;
	 public  float maxspeed = 10;
	 public  float mixspeed = 3;
	 
	// Use this for initialization
	void Start () 
	{
		
		currentspeed = ConnectServer._instance.speed;

	}
	
	// Update is called once per frame
	void Update () 
	{
    transform.Translate(Vector3.left*currentspeed*Time.deltaTime);
   	if (transform.position.x<-8.0)
	{
		transform.position = new Vector3(5.0f,transform.position.y,transform.position.z);
		Start();
	}
    }
}
