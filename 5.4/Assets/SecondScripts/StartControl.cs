using UnityEngine;
using System.Collections;
using UnityEngine.SceneManagement;
public class StartControl : MonoBehaviour {
	bool connectingover=false;
	// Use this for initialization
	void Start () {
	
	}
	
	// Update is called once per frame
	void Update () {

		if (connectingover && ConnectServer._instance.game_start ()) {
			StartCoroutine (startgame ());
		}
	
		if (Input.GetKeyDown (KeyCode.A)) {
		    
			string ip, port;
			InputMessage.getmessage ();
			TextControl.reset ("connect");
			ip = InputMessage.s_ip;
			port = InputMessage.s_port;
			StartCoroutine (wait ());

			if (ConnectServer._instance.Connect (ip, port)) {

				TextControl.reset ("wait");
				connectingover = true;

			} else
				StartCoroutine (wait3());
			
		}

		if (Input.GetKeyDown (KeyCode.B))
			Application.Quit ();
			

	}

	IEnumerator wait(){
		yield return  new  WaitForSeconds(2);
	}
		

	IEnumerator wait3(){
		yield return  new  WaitForSeconds(2);
		TextControl.reset ("fail");
	}

	IEnumerator startgame(){
		yield return  new  WaitForSeconds(2);
		SceneManager.LoadScene (1);
	}

}
