package study.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import study.DataClasses.Good;
import julie.study.R;


public class GoodAdapter extends BaseAdapter  {
    Context ctx;
    LayoutInflater lInflater;
    ArrayList<Good> objects;

   public GoodAdapter(Context context, ArrayList<Good> good) {
        ctx = context;
        objects = good;
        lInflater = (LayoutInflater) ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    // кол-во элементов
    @Override
    public int getCount() {
        return objects.size();
    }

    // элемент по позиции
    @Override
    public Object getItem(int position) {
        return objects.get(position);
    }

    // id по позиции
    @Override
    public long getItemId(int position) {
        return position;
    }

    // пункт списка
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // используем созданные, но не используемые view
        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.good, parent, false);
        }

         Good good = getGood(position);

        // заполняем View в пункте списка данными из товаров: наименование, цена
        // и картинка
        ((TextView) view.findViewById(R.id.title)).setText(good.title);

        if(good.author!=null) {
            ((TextView) view.findViewById(R.id.author)).setVisibility(View.VISIBLE);
            ((TextView) view.findViewById(R.id.author)).setText(good.author);
        }
        else ((TextView) view.findViewById(R.id.author)).setVisibility(View.GONE);

        ((TextView) view.findViewById(R.id.price)).setText(good.price + " руб.");

        return view;
    }

    // товар по позиции
    public Good getGood(int position) {
        return ((Good) getItem(position));
    }

}
