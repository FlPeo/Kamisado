class Kamisado
{
    public static void main (String[] args)
    {
        javax.swing.SwingUtilities.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                Control_Group controler = new Control_Group();
            }
        });
    }
}
