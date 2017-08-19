using UnityEngine;
using System.Collections;
using UnityEngine.UI;
public class UIManager : MonoBehaviour {
	public static int score = 0;
	public Sprite [] list;
	public Image image1, image2;
	//public Text t;
	// Use this for initialization
	void Start () {
	
	}
	
	// Update is called once per frame
	void Update () {
		//t.text = ConnectServer._instance.speed+"";
		int x = score / 10;
		int y = score - x * 10;
		image1.sprite = list [x];
		image2.sprite = list [y];
	}
}
