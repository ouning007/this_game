using UnityEngine;
using System.Collections;
using UnityEngine.SceneManagement;
public class gameControl : MonoBehaviour {
	public static bool over;
	public GameObject panel;
	// Use this for initialization
	void Start () {
		
	}
	
	// Update is called once per frame
	void Update () {
		//if(Input.GetMouseButtonDown(0))
		//	over=true;
		if(Input.GetKeyDown(KeyCode.Q)){
			
			ConnectServer._instance.threadReceive.Abort ();
			ConnectServer._instance.threadHadle.Abort ();
			ConnectServer._instance.client.Close ();
			Application.Quit ();
		}

		if (over == true) {
			panel.SetActive (true);
		}

		if (panel.activeInHierarchy == true) {
			if (Input.GetKeyDown (KeyCode.A)) {
				panel.SetActive (false);
				over = false;
				UIManager.score = 0;
				SceneManager.LoadSceneAsync ("Level1");
			} else if (Input.GetKeyDown (KeyCode.B)) {
				panel.SetActive (false);
				over = false;
				UIManager.score = 0;
				SceneManager.LoadSceneAsync ("start");
			}
		}
	}



}
